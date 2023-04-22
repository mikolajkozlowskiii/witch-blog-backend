package com.example.witchblog.exceptions;

public class JwtTokenException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public JwtTokenException(){super("Token JWT not valid!");}
    public JwtTokenException(String message){super(message);}
}
