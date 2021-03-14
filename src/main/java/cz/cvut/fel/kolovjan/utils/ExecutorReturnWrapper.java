package cz.cvut.fel.kolovjan.utils;

public class ExecutorReturnWrapper {
    private String output;
    private String errorOutput;
    private int exitValue;

    public ExecutorReturnWrapper(String output, String errorOutput, int exitValue) {
        this.output = output;
        this.errorOutput = errorOutput;
        this.exitValue = exitValue;
    }

    public String getOutput() {
        return output;
    }

    public String getErrorOutput() {
        return errorOutput;
    }

    public int getExitValue() {
        return exitValue;
    }

    @Override
    public String toString() {
        return "CommandReturnWrapper{" +
                "output='" + output + '\'' +
                ", errorOutput='" + errorOutput + '\'' +
                ", exitValue=" + exitValue +
                '}';
    }
}
