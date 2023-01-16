package io.SimpleNEM12Parser.exception;

public class SimpleNem12FileParsingException extends RuntimeException {

    public SimpleNem12FileParsingException(String message) {
        super(message);
    }

    public SimpleNem12FileParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
