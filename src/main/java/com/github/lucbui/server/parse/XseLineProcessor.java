package com.github.lucbui.server.parse;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.server.XseDocumentModel;
import com.github.lucbui.server.parse.processors.NoMatchProcessor;
import com.github.lucbui.server.parse.processors.DirectiveProcessors;
import com.github.lucbui.util.CommandUtils;
import org.apache.commons.lang3.StringUtils;

public class XseLineProcessor implements LineProcessor {
    private static final String[] COMMENT_DELIMITERS = {"'", ";", "//"};

    public void processLine(XseDocumentModel document, int linenumber, XseDocumentModel.Line line) {
        String lineString = line.getLine();
        if(!StringUtils.startsWithAny(lineString, COMMENT_DELIMITERS)){
            CommandLine commandLine = CommandUtils.toCommandLine(lineString);
            if(CommandUtils.isPreprocessorDirective(commandLine)){
                DirectiveProcessors.getProcessor(commandLine)
                        .map(DirectiveProcessors::getProcessor)
                        .orElse(NoMatchProcessor.INSTANCE)
                        .process(document, line, commandLine);
            } else {
                //Process actual script commands.
            }
        } else {
            //Comments. We could do things with them, or ignore them.
        }
    }
}
