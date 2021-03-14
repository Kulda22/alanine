package cz.cvut.fel.kolovjan.utils;

import lombok.Getter;

@Getter
public class StatusCommandResponse extends CommandResponse {
    private boolean isDNSListening;
    private boolean isBlockingEnabled;

    public StatusCommandResponse(boolean isSuccessful, String message, boolean isDNSListening, boolean isBlockingEnabled) {
        super(isSuccessful, message);
        this.isDNSListening = isDNSListening;
        this.isBlockingEnabled = isBlockingEnabled;
    }
}
