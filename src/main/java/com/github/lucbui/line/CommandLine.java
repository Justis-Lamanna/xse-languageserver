package com.github.lucbui.line;

public class CommandLine {
    private final String command;
    private final String[] parameters;

    public CommandLine(String command, String[] parameters) {
        this.command = command;
        this.parameters = parameters;
    }

    public CommandLine(String command) {
        this(command, new String[0]);
    }

    public String getCommand() {
        return command;
    }

    public String[] getParameters() {
        return parameters;
    }
}
