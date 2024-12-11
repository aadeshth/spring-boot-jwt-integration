package com.rest.rest_example.exception;

public class InvalidTokenException extends RuntimeException{

    public InvalidTokenException(String msg)
    {
        super(msg);
    }

}
