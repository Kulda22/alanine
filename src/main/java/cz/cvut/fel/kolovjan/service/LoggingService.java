package cz.cvut.fel.kolovjan.service;

import cz.cvut.fel.kolovjan.cli.command.DisableLoggingCommand;
import cz.cvut.fel.kolovjan.cli.command.EnableLoggingCommand;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@AllArgsConstructor
@ApplicationScoped
public class LoggingService {
    private EnableLoggingCommand enableLoggingCommand;
    private DisableLoggingCommand disableLoggingCommand;

    public CommandResponse enableLogging() {
        return enableLoggingCommand.execute();
    }

    public CommandResponse disableLogging(long time) {
        if (time == -1) {
            return disableLoggingCommand.execute();
        } else {
            return disableLoggingCommand.execute(time);
        }
    }
}
