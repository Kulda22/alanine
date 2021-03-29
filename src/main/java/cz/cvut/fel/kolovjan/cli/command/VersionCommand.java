package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.AlanineException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class VersionCommand extends Command {
    public VersionCommand(CommandExecutorInterface commandExecutor) {
        super(commandExecutor);
    }

    public VersionCommand() {
    }


    public CommandResponse execute() {
        ExecutorReturnWrapper returnWrapper = commandExecutor.execute("pihole version -c");
        if (returnWrapper.getExitValue() == 0) {
            return new CommandResponse(true, returnWrapper.getOutput());
        } else {
            throw new AlanineException(returnWrapper.getErrorOutput());
        }
    }
}
