package com.example.witchblog.exceptions;

public class CardNotFoundException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public CardNotFoundException(String message){super(message);}
}
