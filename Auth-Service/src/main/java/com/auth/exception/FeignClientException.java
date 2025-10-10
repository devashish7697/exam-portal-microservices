package com.auth.exception;

public class FeignClientException extends RuntimeException{
    public FeignClientException(String m)
    {
        super(m);
    }
}
