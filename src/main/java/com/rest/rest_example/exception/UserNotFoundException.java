package com.rest.rest_example.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){
       super(message);
    }
}
