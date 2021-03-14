package cz.cvut.fel.kolovjan.cli.executor;

import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.profile.IfBuildProfile;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.ws.rs.Produces;

@Dependent
public class CommandExecutorProvider {


    @Produces
    @IfBuildProfile("dev")
    @ApplicationScoped
    public CommandExecutorInterface dockerCommandExecutor() {
        return new DockerCommandExecutor();
    }


    @Produces
    @DefaultBean
    @ApplicationScoped
    public CommandExecutorInterface commandExecutor() {
        return new BashCommandExecutor();
    }
}