package cz.cvut.fel.kolovjan.service;

import cz.cvut.fel.kolovjan.cli.command.UpdateGravityCommand;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@AllArgsConstructor
@ApplicationScoped
public class GravityService {
    private UpdateGravityCommand updateGravityCommand;

    public CommandResponse updateGravity() {
        return updateGravityCommand.execute();
    }
}
