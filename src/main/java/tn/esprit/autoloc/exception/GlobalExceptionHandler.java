package tn.esprit.autoloc.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.time.ZoneId;

/**
 * Gestion centralisée des erreurs pour tous les contrôleurs REST.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 400 : un ou plusieurs champs du DTO ne respectent pas les contraintes. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
                                                          HttpServletRequest request) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.putIfAbsent(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return build(HttpStatus.BAD_REQUEST, "Les données envoyées sont invalides", request, errors);
    }

    /** 400 : JSON mal formé ou valeur d'énumération inconnue (ex. "categorie": "MOTO"). */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(HttpServletRequest request) {
        return build(HttpStatus.BAD_REQUEST, "Corps de la requête illisible ou valeur inconnue", request, Map.of());
    }

    /** 400 : paramètre d'URL du mauvais type (ex. /agences/abc). */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                            HttpServletRequest request) {
        String message = "Valeur invalide pour le paramètre '" + ex.getName() + "'";
        return build(HttpStatus.BAD_REQUEST, message, request, Map.of());
    }

    /** 404 : la ressource demandée n'existe pas. */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex,
                                                        HttpServletRequest request) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), request, Map.of());
    }

    /** 409 : violation d'une contrainte de la base (doublon d'email, d'immatriculation, ...). */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleConflict(HttpServletRequest request) {
        return build(HttpStatus.CONFLICT,
                "Conflit avec les données existantes (valeur déjà utilisée ou ressource liée)",
                request, Map.of());
    }

    /** 500 : toute autre erreur inattendue. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleOther(Exception ex, HttpServletRequest request) {
        log.error("Erreur inattendue sur {}", request.getRequestURI(), ex);
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur interne est survenue", request, Map.of());
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message,
                                                HttpServletRequest request, Map<String, String> errors) {
        ErrorResponse body = new ErrorResponse(
                LocalDateTime.now(ZoneId.of("Africa/Tunis")),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI(),
                errors);
        return ResponseEntity.status(status).body(body);
    }
}