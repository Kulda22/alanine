package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.PluginException;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import cz.cvut.fel.kolovjan.utils.LoggingStatusCommandResponse;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class LoggingStatusCommand extends Command {
    public LoggingStatusCommand() {
    }

    public LoggingStatusCommand(CommandExecutorInterface commandExecutor) {
        super(commandExecutor);
    }

    public LoggingStatusCommandResponse execute() {
        ExecutorReturnWrapper loggingEnabledReturnWrapper = commandExecutor.execute("cat /etc/pihole/setupVars.conf");
        if (loggingEnabledReturnWrapper.getExitValue() == 0) {
            boolean isLoggingEnabled = loggingEnabledReturnWrapper.getOutput().contains("QUERY_LOGGING=true");
            return new LoggingStatusCommandResponse(true, "successfully got logging status", isLoggingEnabled);
        } else {
            throw new PluginException(loggingEnabledReturnWrapper.getErrorOutput());
        }
    }
}
