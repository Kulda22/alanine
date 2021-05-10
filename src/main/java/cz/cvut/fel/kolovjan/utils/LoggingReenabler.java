package cz.cvut.fel.kolovjan.utils;

import cz.cvut.fel.kolovjan.cli.command.EnableLoggingCommand;
import io.quarkus.scheduler.Scheduled;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.time.LocalDateTime;

/**
 * This class is used for disabling logging for given time.
 */
@ApplicationScoped
@Slf4j
public class LoggingReenabler {
    private EnableLoggingCommand enableLoggingCommand;

    private LocalDateTime enableTime = LocalDateTime.MAX;

    @Inject
    public LoggingReenabler(EnableLoggingCommand enableLoggingCommand) {
        this.enableLoggingCommand = enableLoggingCommand;
    }

    /**
     * Set this class to enable logging after given time.
     *
     * @param minutes time until enabled
     */
    public void enableAfter(long minutes) {
        enableTime = LocalDateTime.now().plusMinutes(minutes);

        log.info("setting time to enable logging to {}", enableTime);
    }


    @Scheduled(every = "1m")
    public void checkIfShouldEnable() {
        if (LocalDateTime.now().isAfter(enableTime)) {
            log.info("resuming logging");
            enableLoggingCommand.execute();
            enableTime = LocalDateTime.MAX;
        }
    }

}
