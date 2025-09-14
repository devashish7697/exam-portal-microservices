package com.user.exception;

import lombok.Data;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
public class ApiError {
    private Instant timestamp = Instant.now();
    private int status;
    private String error;
    private List<String> details;

    public ApiError(int status, String error, List<String> details) {
        this.status = status;
        this.error = error;
        this.details = details;
    }
}

