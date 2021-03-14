package cz.cvut.fel.kolovjan.cli.executor;


import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import org.jboss.logging.Logger;

public class DummyExecutor implements CommandExecutorInterface {
    private static final Logger logger = Logger.getLogger(DummyExecutor.class);


    @Override
    public ExecutorReturnWrapper execute(String command) {
        logger.info("Dummy executor executes " + command);
        logger.info("sleeping");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            logger.error(e);
        }

        return new ExecutorReturnWrapper("dummy executor", "", 0);
    }
}
