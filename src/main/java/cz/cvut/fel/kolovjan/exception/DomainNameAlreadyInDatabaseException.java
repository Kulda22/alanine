package cz.cvut.fel.kolovjan.exception;

public class DomainNameAlreadyInDatabaseException extends PluginException {
    public DomainNameAlreadyInDatabaseException(String message) {
        super(message);
    }
}
