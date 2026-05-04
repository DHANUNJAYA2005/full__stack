package com.campus.events.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public Object handleEventNotFoundException(EventNotFoundException ex, HttpServletRequest request) {
        if (isApiRequest(request)) {
            return buildResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage());
        }
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("message", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(AlreadyRegisteredException.class)
    public Object handleAlreadyRegisteredException(AlreadyRegisteredException ex, HttpServletRequest request) {
        if (isApiRequest(request)) {
            return buildResponseEntity(HttpStatus.CONFLICT, ex.getMessage());
        }
        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("message", ex.getMessage());
        return mav;
    }

    @ExceptionHandler(Exception.class)
    public Object handleGlobalException(Exception ex, HttpServletRequest request) {
        if (isApiRequest(request)) {
            return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + ex.getMessage());
        }
        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("message", "An unexpected error occurred: " + ex.getMessage());
        return mav;
    }

    private boolean isApiRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/api/");
    }

    private ResponseEntity<Map<String, String>> buildResponseEntity(HttpStatus status, String message) {
        Map<String, String> body = new HashMap<>();
        body.put("error", message);
        return new ResponseEntity<>(body, status);
    }
}
