package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.AlanineException;
import cz.cvut.fel.kolovjan.exception.PiholeAlreadyDisabledException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import cz.cvut.fel.kolovjan.utils.TimeUnitEnum;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@ApplicationScoped
public class DisableCommand extends Command {
    private final static String PIHOLE_DISABLE_COMMAND = "pihole disable ";

    //    In CDI, a normal scoped bean must always declare a no-args constructor (this constructor is normally generated by the compiler unless you declare any other constructor). However, this requirement complicates constructor injection - you would need to provide a dummy no-args constructor to make things work in CDI.
    public DisableCommand() {
        super(null);
    }

    public DisableCommand(CommandExecutorInterface commandExecutor) {
        super(commandExecutor);
    }

    /**
     * Disable Pi-hole indefinitely
     *
     * @return
     */
    public CommandResponse execute() {
        return executeCommand(PIHOLE_DISABLE_COMMAND);
    }

        /*
        funny thing, if you disable with time, and then try to disable pihole will act as if you are stupid and
        return already disabled. But after given time the pihole will enable itself ! what a nice feature !
         */

    public CommandResponse execute(long time, TimeUnitEnum timeUnit) {
        return executeCommand(PIHOLE_DISABLE_COMMAND + time + timeUnit.getTimeUnit());
    }

    private CommandResponse executeCommand(String command) {
        ExecutorReturnWrapper returnWrapper = commandExecutor.execute(command);

        /// no error execution
        if (returnWrapper.getExitValue() == 0) {
            if (returnWrapper.getOutput().contains("Pi-hole Disabled")) {
                return new CommandResponse(true, "Pi-hole Disabled");

            } else if (returnWrapper.getOutput().contains("Blocking already disabled, nothing to do")) {
                /// pihole was already disabled
                throw new PiholeAlreadyDisabledException();
            } else {
                log.error("unknown output of disable : " + returnWrapper.getErrorOutput());
                throw new AlanineException("unknown output : " + returnWrapper.getErrorOutput());

            }
        } else {
            throw new AlanineException(returnWrapper.getErrorOutput());
        }
    }
}
