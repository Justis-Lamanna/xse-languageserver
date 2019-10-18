package com.github.lucbui.server.parse.processors;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.server.XseDocumentModel;

public interface Processor {
    void process(XseDocumentModel document, int linenumber, XseDocumentModel.Line line, CommandLine commandLine);

    void deprocess(XseDocumentModel document, int linenumber, XseDocumentModel.Line line, CommandLine commandLine);
}
