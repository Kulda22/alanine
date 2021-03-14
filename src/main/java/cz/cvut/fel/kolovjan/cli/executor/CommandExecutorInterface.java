package cz.cvut.fel.kolovjan.cli.executor;


import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;

public interface CommandExecutorInterface {

    public ExecutorReturnWrapper execute(String command);
}
