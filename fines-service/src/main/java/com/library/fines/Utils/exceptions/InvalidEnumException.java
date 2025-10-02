package com.library.fines.Utils.exceptions;

public class InvalidEnumException extends RuntimeException{

    public InvalidEnumException() {}

    public InvalidEnumException(String message) { super(message); }

    public InvalidEnumException(Throwable cause) { super(cause); }

    public InvalidEnumException(String message, Throwable cause) { super(message, cause); }
}
