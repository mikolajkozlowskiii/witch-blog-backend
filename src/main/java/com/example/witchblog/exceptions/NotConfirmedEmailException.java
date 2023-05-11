package com.example.witchblog.exceptions;

public class NotConfirmedEmailException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public NotConfirmedEmailException(String email){super("Email " + email + " hasn't been confirmed!");}
}

