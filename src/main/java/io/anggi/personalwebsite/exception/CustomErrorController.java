package io.anggi.personalwebsite.exception;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("error")
    public String handle404Error(Model model, HttpServletRequest request, HttpServletResponse response, WebRequest webRequest) {
        Integer status = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorMessage = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        ErrorDetails errorDetails = new ErrorDetails(new Date(), "Page Not found :(", webRequest.getDescription(true));

        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("errorMessage", errorDetails.getMessage());
        model.addAttribute("timestamp", errorDetails.getTimestamp() );
        model.addAttribute("details", errorDetails.getDetails());

        response.setStatus(status);
        return "error";
    }
}
