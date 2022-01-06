package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.AlanineException;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import cz.cvut.fel.kolovjan.utils.StatusCommandResponse;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class StatusCommand extends Command {
    public StatusCommand(CommandExecutorInterface commandExecutor) {
        super(commandExecutor);
    }

    public StatusCommand() {
        super(null);
    }

    /**
     * Get status of Pi-hole.
     *
     * @return
     */
    public StatusCommandResponse execute() {
        ExecutorReturnWrapper statusReturnWrapper = commandExecutor.execute("pihole status");
        if (statusReturnWrapper.getExitValue() == 0) {
            boolean isDnsListening = statusReturnWrapper.getOutput().contains("FTL is listening");
            boolean isBlockingEnabled = statusReturnWrapper.getOutput().contains("Pi-hole blocking is enabled");
            return new StatusCommandResponse(true, "status queried successfully", isDnsListening, isBlockingEnabled);
        } else {
            throw new AlanineException(statusReturnWrapper.getErrorOutput());
        }
    }
}
