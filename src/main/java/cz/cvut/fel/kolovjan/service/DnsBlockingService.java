package cz.cvut.fel.kolovjan.service;

import cz.cvut.fel.kolovjan.cli.command.DisableCommand;
import cz.cvut.fel.kolovjan.cli.command.EnableCommand;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import cz.cvut.fel.kolovjan.utils.TimeUnitEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@AllArgsConstructor
@ApplicationScoped
public class DnsBlockingService {
    private DisableCommand disableCommand;
    private EnableCommand enableCommand;


    public CommandResponse enableLogging() {
        return enableCommand.execute();
    }

    public CommandResponse disableLogging(long time, TimeUnitEnum timeUnitEnum) {
        if (time == -1) {
            return disableCommand.execute();
        } else {
            return disableCommand.execute(time, timeUnitEnum);
        }
    }

}
