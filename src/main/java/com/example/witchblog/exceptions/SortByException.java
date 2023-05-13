package com.example.witchblog.exceptions;

public class SortByException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public SortByException(String sortType){super("Invalid sort type: " + sortType);}
}


