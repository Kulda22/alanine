package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.AlanineException;
import cz.cvut.fel.kolovjan.exception.GravityUpdateFailException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class UpdateGravityCommand extends Command {

    public UpdateGravityCommand(CommandExecutorInterface commandExecutor) {
        super(commandExecutor);
    }

    public UpdateGravityCommand() {
        super(null);
    }

    /// todo add timer ?
    public CommandResponse execute() {
        ExecutorReturnWrapper executorReturnWrapper = commandExecutor.execute("pihole -g");

        /// TODO gravity output is not nice so only rudimentary check is done
        if (executorReturnWrapper.getExitValue() == 0) {

            if (executorReturnWrapper.getOutput().contains("Swapping databases")) {
                return new CommandResponse(true, "gravity updated successfully");
            } else {
                log.warn("unknown output of gravity update: " + executorReturnWrapper.getErrorOutput());
                throw new AlanineException("gravity update may have stumbled across problems, please try to update via web admin or pihole command - unknown output: " + executorReturnWrapper
                        .getErrorOutput());
            }
        } else {
            throw new GravityUpdateFailException(
                    "gravity update may have stumbled across problems, please try to update via web admin or pihole command ");
        }
    }
}
