package com.library.catalogs.Utils.exceptions;

public class NoMoreBookAvailableException extends RuntimeException{

    public NoMoreBookAvailableException() {}

    public NoMoreBookAvailableException(String message) { super(message); }

    public NoMoreBookAvailableException(Throwable cause) { super(cause); }

    public NoMoreBookAvailableException(String message, Throwable cause) { super(message, cause); }
}
