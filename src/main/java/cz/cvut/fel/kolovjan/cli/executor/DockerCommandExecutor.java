package cz.cvut.fel.kolovjan.cli.executor;

import cz.cvut.fel.kolovjan.utils.ExecutorReturnWrapper;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class DockerCommandExecutor implements CommandExecutorInterface {


    private static final String DOCKER_PRE = "docker exec pihole ";

    /// TODO : better way to make sure we won't try two commands at same time
    public synchronized ExecutorReturnWrapper execute(String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        log.debug("Docker executing command {} ", DOCKER_PRE + command);
        processBuilder.command("bash", "-c", DOCKER_PRE + command);

        StringBuilder output = new StringBuilder();
        StringBuilder errorOutput = new StringBuilder();
        int exitVal = -1;
        try {

            Process process = processBuilder.start();


            BufferedReader outputReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = outputReader.readLine()) != null) {
                output.append(line).append("\n");
            }
            while ((line = errorReader.readLine()) != null) {
                errorOutput.append(line).append("\n");
            }


            exitVal = process.waitFor();
            if (exitVal == 0) {
                log.debug("Success in executing command {}", DOCKER_PRE + command);
                return new ExecutorReturnWrapper(output.toString(), errorOutput.toString(), exitVal);
            } else {
                log.info("No Success in executing command : {} \n Output: {} \n error output : {}", command, output, errorOutput);
            }

        } catch (IOException | InterruptedException e) {
            log.error(e.toString());
        }
        return new ExecutorReturnWrapper(output.toString(), errorOutput.toString(), exitVal);
    }
}