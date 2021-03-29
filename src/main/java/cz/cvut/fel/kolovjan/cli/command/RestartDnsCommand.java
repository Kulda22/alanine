package cz.cvut.fel.kolovjan.cli.command;

import cz.cvut.fel.kolovjan.cli.executor.CommandExecutorInterface;
import cz.cvut.fel.kolovjan.exception.AlanineException;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@Slf4j
public class RestartDnsCommand extends Command {
    public RestartDnsCommand(CommandExecutorInterface commandExecutor) {
        super(commandExecutor);
    }

    //    In CDI, a normal scoped bean must always declare a no-args constructor (this constructor is normally generated by the compiler unless you declare any other constructor). However, this requirement complicates constructor injection - you would need to provide a dummy no-args constructor to make things work in CDI.
    public RestartDnsCommand() {
        super();
    }

    public CommandResponse execute() {
        ExecutorReturnWrapper executorReturnWrapper = commandExecutor.execute("pihole restartdns");
        if (executorReturnWrapper.getExitValue() == 0) {
            return new CommandResponse(true, "Restart completed successfully");
        } else {
            String msg = "DNS Restart failed with return code: " + executorReturnWrapper.getExitValue() + " and error output :" + executorReturnWrapper
                    .getErrorOutput();
            throw new AlanineException(msg);
        }
    }
}
