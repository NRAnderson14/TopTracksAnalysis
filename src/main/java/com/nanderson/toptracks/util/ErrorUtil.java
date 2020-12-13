package com.nanderson.toptracks.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorUtil {

    private ErrorUtil() {
    }

    public static ResponseEntity<String> convertExceptionToResponse(Exception exception, HttpStatus theStatusToUse) {
        return ResponseEntity.status(theStatusToUse).body(exception.getMessage());
    }
}
