package com.example.newapplication;

public class NoAttemptsException extends RuntimeException{
    public NoAttemptsException(){super();}

    public NoAttemptsException(String message){super(message);}
}
