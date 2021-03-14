package cz.cvut.fel.kolovjan.utils;

public class LoggingStatusCommandResponse extends CommandResponse {
    private boolean isLoggingEnabled ;

    public LoggingStatusCommandResponse(boolean isSuccessful, String message, boolean isLoggingEnabled) {
        super(isSuccessful, message);
        this.isLoggingEnabled = isLoggingEnabled;
    }

    public boolean isLoggingEnabled() {
        return isLoggingEnabled;
    }
}
