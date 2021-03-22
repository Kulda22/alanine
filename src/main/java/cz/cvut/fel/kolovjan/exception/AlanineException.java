package cz.cvut.fel.kolovjan.exception;

public class AlanineException extends RuntimeException {
    public AlanineException() {
    }

    public AlanineException(String message) {
        super(message);
    }

    public AlanineException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlanineException(Throwable cause) {
        super(cause);
    }

    public AlanineException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
