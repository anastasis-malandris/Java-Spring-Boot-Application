package com.example.lesson.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class LessonNotFoundException extends Exception {
    public LessonNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
