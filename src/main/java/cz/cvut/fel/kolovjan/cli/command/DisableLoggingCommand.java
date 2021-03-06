package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.AlanineException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import cz.cvut.fel.kolovjan.utils.LoggingReenabler;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@Slf4j
@ApplicationScoped
public class DisableLoggingCommand extends Command {

    private LoggingReenabler reenabler;

    @Inject
    public DisableLoggingCommand(CommandExecutorInterface commandExecutor, LoggingReenabler reenabler) {
        super(commandExecutor);
        this.reenabler = reenabler;
    }

    //    In CDI, a normal scoped bean must always declare a no-args constructor (this constructor is normally generated by the compiler unless you declare any other constructor). However, this requirement complicates constructor injection - you would need to provide a dummy no-args constructor to make things work in CDI.
    public DisableLoggingCommand() {
        super();
    }

    /**
     * Disable Pi-hole logging indefinitely
     *
     * @return Result with message
     */
    public CommandResponse execute() {
        ExecutorReturnWrapper executorReturnWrapper = commandExecutor.execute("pihole logging off noflush");
        /// no error and correct message
        if (executorReturnWrapper.getOutput().contains("Logging has been disabled!")) {
            return new CommandResponse(true, "Logging has been disabled!");
            /// some kind of error
        } else {
            throw new AlanineException(executorReturnWrapper.getErrorOutput());
        }
    }

    /**
     * Disable Pi-hole logging for given time in minutes
     *
     * @param time time to disable in minutes
     * @return Result with message
     */
    public CommandResponse execute(long time) {
        CommandResponse toRet = execute();

        reenabler.enableAfter(time);
        return new CommandResponse(toRet.isSuccessful(), "Logging disabled for " + time + " minutes");
    }
}