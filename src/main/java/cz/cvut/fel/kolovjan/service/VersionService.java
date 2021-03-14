package cz.cvut.fel.kolovjan.service;

import cz.cvut.fel.kolovjan.cli.command.VersionCommand;
import cz.cvut.fel.kolovjan.utils.CommandResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@Slf4j
@AllArgsConstructor
@ApplicationScoped
public class VersionService {
    private VersionCommand versionCommand;

    public CommandResponse getVersion() {
        return versionCommand.execute();
    }


}
