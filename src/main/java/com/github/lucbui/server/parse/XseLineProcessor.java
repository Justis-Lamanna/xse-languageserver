package com.github.lucbui.server.parse;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.server.XseDocumentModel;
import com.github.lucbui.server.parse.processors.NoMatchProcessor;
import com.github.lucbui.server.parse.processors.DirectiveProcessors;
import com.github.lucbui.server.parse.processors.Processor;
import com.github.lucbui.util.CommandUtils;
import org.apache.commons.lang3.StringUtils;

public class XseLineProcessor implements LineProcessor {
    private static final String[] COMMENT_DELIMITERS = {"'", ";", "//"};

    public void processLine(XseDocumentModel document, int linenumber, XseDocumentModel.Line line) {
        if(!isComment(line.getLine())){
            CommandLine commandLine = CommandUtils.toCommandLine(line.getLine());
            getProcessorFor(commandLine).process(document, linenumber, line, commandLine);
        }
    }

    @Override
    public void deprocessLine(XseDocumentModel document, int linenumber, XseDocumentModel.Line line) {
        if(!isComment(line.getLine())){
            CommandLine commandLine = CommandUtils.toCommandLine(line.getLine());
            getProcessorFor(commandLine).deprocess(document, linenumber, line, commandLine);
        }
    }

    private boolean isComment(String line){
        return StringUtils.startsWithAny(line, COMMENT_DELIMITERS);
    }

    private Processor getProcessorFor(CommandLine commandLine){
        if(CommandUtils.isPreprocessorDirective(commandLine)) {
            return DirectiveProcessors.getProcessor(commandLine)
                    .map(DirectiveProcessors::getProcessor)
                    .orElse(NoMatchProcessor.INSTANCE);
        } else {
            return NoMatchProcessor.INSTANCE;
        }
    }
}
