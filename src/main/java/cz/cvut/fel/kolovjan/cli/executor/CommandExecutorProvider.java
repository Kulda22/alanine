package cz.cvut.fel.kolovjan.cli.executor;

import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.profile.IfBuildProfile;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.ws.rs.Produces;

/**
 * This class is used as provider for DI. We create executor based on what maven profile is active.
 */
@Dependent
@Slf4j
public class CommandExecutorProvider {
    @ConfigProperty(name = "alanine.executor.docker.command", defaultValue = "docker exec pihole")
    String DOCKER_COMMAND_PREPOSITION;


    @Produces
    @IfBuildProfile("dev")
    @ApplicationScoped
    public CommandExecutorInterface dockerCommandExecutor() {
        log.info("Running docker executor");
        return new DockerCommandExecutor(DOCKER_COMMAND_PREPOSITION);
    }


    /// we test docker image
    @Produces
    @IfBuildProfile("test")
    @ApplicationScoped
    public CommandExecutorInterface dockerCommandTestExecutor() {
        log.info("Running docker executor");
        return new DockerCommandExecutor(DOCKER_COMMAND_PREPOSITION);
    }


    @Produces
    @DefaultBean
    @ApplicationScoped
    public CommandExecutorInterface commandExecutor() {
        log.info("Running bash executor");
        return new BashCommandExecutor();
    }
}