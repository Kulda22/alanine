package cz.cvut.fel.kolovjan.cli.executor;

import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.profile.IfBuildProfile;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.ws.rs.Produces;

@Dependent
@Slf4j
public class CommandExecutorProvider {


    @Produces
    @IfBuildProfile("dev")
    @ApplicationScoped
    public CommandExecutorInterface dockerCommandExecutor() {
        log.info("Running docker executor");
        return new DockerCommandExecutor();
    }


    /// we test docker image
    @Produces
    @IfBuildProfile("test")
    @ApplicationScoped
    public CommandExecutorInterface dockerCommandTestExecutor() {
        log.info("Running docker executor");
        return new DockerCommandExecutor();
    }


    @Produces
    @DefaultBean
    @ApplicationScoped
    public CommandExecutorInterface commandExecutor() {
        log.info("Running bash executor");
        return new BashCommandExecutor();
    }
}