package com.auth.exception;

import java.time.Instant;
import java.util.List;

public class ApiError {
    private Instant timestamp = Instant.now();
    private int status;
    private String error;
    private List<String> details;

    public ApiError(int status, String error, List<String> details) {
        this.status = status; this.error = error; this.details = details;
    }
    public Instant getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getError() { return error; }
    public List<String> getDetails() { return details; }
}

