package com.github.lucbui.util;

import com.github.lucbui.line.CommandLine;

import java.util.regex.Pattern;

/**
 * Utilities for commands
 */
public class CommandUtils {
    private static final Pattern SPACES = Pattern.compile("\\s+");
    private static final String PREPROCESSING_DIRECTIVE_DELIMITER = "#";
    /**
     * Convert a string to a generic command
     * @param line The line to convert
     * @return A CommandLine, broken into command and parameters
     */
    public static CommandLine toCommandLine(String line) {
       if(line == null){
           return new CommandLine("");
       }
        String[] commandAndParams = SPACES.split(line, 2);
        if(commandAndParams.length > 1){
            String[] params = SPACES.split(commandAndParams[1]);
            return new CommandLine(commandAndParams[0], params);
        } else {
            return new CommandLine(commandAndParams[0]);
        }
    }

    public static boolean isPreprocessorDirective(CommandLine command){
        if(command == null){
            return false;
        }
        return command.getCommand().startsWith(PREPROCESSING_DIRECTIVE_DELIMITER);
    }
}
