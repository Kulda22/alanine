package cz.cvut.fel.kolovjan.utils;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;

@RegisterForReflection
@AllArgsConstructor
@Getter
public class CommandResponse {
    private final boolean isSuccessful;
    private final String message;
}
