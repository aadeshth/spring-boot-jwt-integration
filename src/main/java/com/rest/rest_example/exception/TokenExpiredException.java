package com.rest.rest_example.exception;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException(String msg)
    {
        super(msg);
    }
}
