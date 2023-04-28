package net.posco.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null && Integer.valueOf(status.toString()) == HttpStatus.BAD_REQUEST.value()) {
            return "error/400";
        }
        else if (status != null && Integer.valueOf(status.toString()) == HttpStatus.FORBIDDEN.value()) {
            return "error/403";
        }
        else if (status != null && Integer.valueOf(status.toString()) == HttpStatus.NOT_FOUND.value()) {
            return "error/404";
        }   
        else if (status != null && Integer.valueOf(status.toString()) == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            return "error/503";
        }
        return "error/500";
    }
}
