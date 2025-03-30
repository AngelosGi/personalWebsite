package io.anggi.personalwebsite.exception;

import jakarta.servlet.http.HttpServletRequest; // Import HttpServletRequest
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Import ResponseEntity
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView; // Import ModelAndView for flexibility

import java.time.LocalDateTime; // Import LocalDateTime

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String API_PATH_PREFIX = "/api/";

    // Helper method to check if the request is for an API endpoint
    private boolean isApiRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith(API_PATH_PREFIX);
    }

    // Helper method to create ErrorDetails
    private ErrorDetails createErrorDetails(String message, WebRequest request) {
        return new ErrorDetails(LocalDateTime.now(), message, request.getDescription(false)); // Use LocalDateTime and false for details
    }

    // Helper method to build response (either JSON or HTML view)
    private Object buildErrorResponse(HttpStatus status, String message, WebRequest webRequest, HttpServletRequest httpRequest, Model model) {
        ErrorDetails errorDetails = createErrorDetails(message, webRequest);

        if (isApiRequest(httpRequest)) {
            // Return JSON for API requests
            return new ResponseEntity<>(errorDetails, status);
        } else {
            // Return HTML view name for UI requests
            model.addAttribute("status", status.value());
            model.addAttribute("error", status.getReasonPhrase()); // Add reason phrase
            model.addAttribute("errorMessage", errorDetails.getMessage());
            model.addAttribute("timestamp", errorDetails.getTimestamp());
            // model.addAttribute("details", errorDetails.getDetails()); // Details might be less relevant for UI
            return "error"; // Return view name
        }
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public Object handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request, HttpServletRequest httpRequest, Model model) {
        // Note: @ResponseStatus on the exception itself might handle simple cases,
        // but this allows consistent response structure (JSON vs HTML)
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, httpRequest, model);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Object handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request, HttpServletRequest httpRequest, Model model) {
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        String message = String.format("Invalid input format for parameter '%s'. Expected type: %s. Actual value: '%s'",
                                       ex.getName(), requiredType, ex.getValue());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message, request, httpRequest, model);
    }

    // Optional: Add a generic handler for other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public Object handleGenericException(Exception ex, WebRequest request, HttpServletRequest httpRequest, Model model) {
        // Log the full exception for debugging purposes
        // logger.error("Unhandled exception occurred", ex); // Assuming you have a logger instance
        String message = "An unexpected error occurred. Please try again later.";
        // Avoid exposing internal exception details to the client unless necessary
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message, request, httpRequest, model);
    }
}