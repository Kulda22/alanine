package cz.cvut.fel.kolovjan.exception;

public class InvalidDomainNameException extends PluginException {
    public InvalidDomainNameException(String domainName) {
        super("Domain name " + domainName + " is invalid");
    }
}
