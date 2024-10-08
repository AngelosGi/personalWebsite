package io.anggi.personalwebsite.exception;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletResponse response, Model model, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(true));

        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("errorMessage", errorDetails.getMessage());
        model.addAttribute("timestamp", errorDetails.getTimestamp());
        model.addAttribute("details", errorDetails.getDetails());

        response.setStatus(HttpStatus.NOT_FOUND.value());
        return "error";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletResponse response, Model model, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Invalid input format for parameter '" + ex.getName() + "'. Expected type: Integer", request.getDescription(true));

        model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("errorMessage", errorDetails.getMessage());
        model.addAttribute("timestamp", errorDetails.getTimestamp());
        model.addAttribute("details", errorDetails.getDetails());

        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return "error";

    }
}
