package com.github.lucbui.server.parse;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.line.UnfinishedCommand;
import com.github.lucbui.server.XseDocumentModel;
import com.github.lucbui.server.parse.directive.DirectiveProcessor;
import com.github.lucbui.server.parse.directive.DirectiveProcessors;
import com.github.lucbui.util.CommandUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class XseLineProcessor implements LineProcessor {
    private static final String[] COMMENT_DELIMITERS = {"'", ";", "//"};

    public void processLine(XseDocumentModel document, int linenumber, XseDocumentModel.Line line) {
        String lineString = line.getLine();
        if(!StringUtils.startsWithAny(lineString, COMMENT_DELIMITERS)){
            CommandLine commandLine = CommandUtils.toCommandLine(lineString);
            if(CommandUtils.isPreprocessorDirective(commandLine)){
                List<DirectiveProcessors> processors = DirectiveProcessors.getProcessor(commandLine);
                if(processors.isEmpty()){
                    //TODO: Diagnose. Not enough/too many params? Not a real directive?
                } else if(processors.size() == 1){
                    DirectiveProcessor processor = processors.get(0).getProcessor();
                    //TODO: Process!
                } else {
                    throw new IllegalStateException("2+ processors matched: " + processors.toString());
                }
            }
        }
    }
}
