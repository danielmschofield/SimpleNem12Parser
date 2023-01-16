package io.SimpleNEM12Parser.exception;

public class SimpleNem12FileFormatException extends RuntimeException {

    public SimpleNem12FileFormatException(String message) {
        super(message);
    }

    public SimpleNem12FileFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
