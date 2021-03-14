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

    protected String sanitizeString(String unsanitized) {
        return unsanitized.replaceAll(REPLACEMENT_REGEX, "\\\\$1");
    }
}