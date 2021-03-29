package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.inject.Inject;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Command {
    protected final static String REPLACEMENT_REGEX = "([+!\\(\\){}\\[\\]^\"~<|;>*?:\\\\]|[&\\|]{2})";

    @Inject
    protected CommandExecutorInterface commandExecutor;

    /**
     * Sanitize special bash characters
     *
     * @param unsanitized string to be sanitized
     * @return sanitized string
     */
    protected String sanitizeString(String unsanitized) {
        return unsanitized.replaceAll(REPLACEMENT_REGEX, "\\\\$1");
    }

    /**
     * check if domain could contain some sort of bash injection
     *
     * @param domain Domain to be checked
     * @return true if domain could be malicious
     */

    protected boolean checkIfDomainNameIsMalicious(String domain) {
        return domain.contains(" ");
    }

}