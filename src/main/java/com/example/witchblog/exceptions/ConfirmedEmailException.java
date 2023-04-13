package com.example.witchblog.exceptions;

public class ConfirmedEmailException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public ConfirmedEmailException(){super("Email is already confirmed!");}
}
