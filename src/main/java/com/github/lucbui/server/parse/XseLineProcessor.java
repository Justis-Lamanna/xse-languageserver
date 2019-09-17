package com.github.lucbui.server.parse;

import com.github.lucbui.server.XseDocumentModel;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class XseLineProcessor implements LineProcessor {

    private static final String[] COMMENT_DELIMITERS = {"'", ";", "//"};
    private static final String PREPROCESSING_DIRECTIVE_DELIMITER = "#";

    private static final Pattern SPACES = Pattern.compile("\\s+");

    public void processLine(XseDocumentModel document, int linenumber, XseDocumentModel.Line line) {
        String lineString = line.getLine();
        if(StringUtils.startsWith(lineString, PREPROCESSING_DIRECTIVE_DELIMITER)){
            CommandLine commandLine = toCommandLine(lineString);
            //More processing here...
        }
    }

    private CommandLine toCommandLine(String line){
        String[] commandAndParams = SPACES.split(line, 2);
        if(commandAndParams.length > 1){
            String[] params = SPACES.split(commandAndParams[1]);
            return new CommandLine(commandAndParams[0], params);
        } else {
            return new CommandLine(commandAndParams[0]);
        }
    }

    private static class CommandLine {
        private String command;
        private String[] parameters;

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
}
