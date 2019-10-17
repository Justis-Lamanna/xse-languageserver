package com.github.lucbui.server.parse.processors;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.server.XseDocumentModel;

public class NoMatchProcessor implements Processor {
    public static final NoMatchProcessor INSTANCE = new NoMatchProcessor();

    @Override
    public void process(XseDocumentModel document, XseDocumentModel.Line line, CommandLine commandLine) {

    }
}
