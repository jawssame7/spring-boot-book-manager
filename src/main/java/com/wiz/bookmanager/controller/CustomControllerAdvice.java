package com.wiz.bookmanager.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomControllerAdvice {

    /**
     * Exception 発生時 ここがコールされる
     * @param e
     * @return
     */
//    @ExceptionHandler
//    public String handleException(Exception e) {
//        return "error/index.html";
//    }
}
