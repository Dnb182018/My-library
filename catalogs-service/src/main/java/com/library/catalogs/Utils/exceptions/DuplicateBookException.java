package com.library.catalogs.Utils.exceptions;

public class DuplicateBookException extends RuntimeException{

    public DuplicateBookException() {}

    public DuplicateBookException(String message) { super(message); }

    public DuplicateBookException(Throwable cause) { super(cause); }

    public DuplicateBookException(String message, Throwable cause) { super(message, cause); }
}
