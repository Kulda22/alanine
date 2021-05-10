package cz.cvut.fel.kolovjan.service;

import cz.cvut.fel.kolovjan.cli.command.LoggingStatusCommand;
import cz.cvut.fel.kolovjan.cli.command.StatusCommand;
import cz.cvut.fel.kolovjan.utils.LoggingStatusCommandResponse;
import cz.cvut.fel.kolovjan.utils.StatusCommandResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@AllArgsConstructor
@ApplicationScoped
public class StatusService {
    private StatusCommand statusCommand;
    private LoggingStatusCommand loggingStatusCommand;

/// to do - chain it ? https://www.baeldung.com/java-completablefuture
public Map<String, Boolean> getStatusMap() {
    HashMap<String, Boolean> map = new HashMap<>();

    CompletableFuture<StatusCommandResponse> statusFuture = CompletableFuture.supplyAsync(() -> statusCommand.execute());
    CompletableFuture<LoggingStatusCommandResponse> loggingStatusFuture = CompletableFuture.supplyAsync(() -> loggingStatusCommand
            .execute());


    try {

        map.put("isDNSListening", statusFuture.get().isDNSListening());
        map.put("isBlockingEnabled", statusFuture.get().isBlockingEnabled());
            map.put("isLoggingEnabled", loggingStatusFuture.get().isLoggingEnabled());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return map;
    }

}