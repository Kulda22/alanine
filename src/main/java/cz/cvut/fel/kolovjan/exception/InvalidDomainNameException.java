package cz.cvut.fel.kolovjan.exception;

public class InvalidDomainNameException extends AlanineException {
    public InvalidDomainNameException(String domainName) {
        super("Domain name " + domainName + " is invalid");
    }
}
