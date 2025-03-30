package io.anggi.personalwebsite.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest; 

import java.time.LocalDateTime; 
@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error") 
    public String handleError(HttpServletRequest request, Model model) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = (statusObj instanceof Integer) ? (Integer) statusObj : null;
        HttpStatus status = (statusCode != null) ? HttpStatus.resolve(statusCode) : HttpStatus.INTERNAL_SERVER_ERROR;
        if (status == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR; // Handle unknown status codes
        }

        String errorMessage;
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        if (exception instanceof Throwable) {
            errorMessage = status.getReasonPhrase(); 
        } else {
            Object messageAttr = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
            errorMessage = (messageAttr instanceof String && !((String) messageAttr).isEmpty()) ? (String) messageAttr : status.getReasonPhrase();
        }

        // Use ServletWebRequest for description if needed, false for less detail
        ServletWebRequest webRequest = new ServletWebRequest(request);
        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), errorMessage, webRequest.getDescription(false));

        model.addAttribute("status", status.value());
        model.addAttribute("error", status.getReasonPhrase()); // Add reason phrase
        model.addAttribute("errorMessage", errorDetails.getMessage());
        model.addAttribute("timestamp", errorDetails.getTimestamp());
        model.addAttribute("details", errorDetails.getDetails()); // Optional

        return "error"; // Render the error template
    }


}