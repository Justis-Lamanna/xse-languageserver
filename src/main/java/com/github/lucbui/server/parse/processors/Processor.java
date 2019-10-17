package com.github.lucbui.server.parse.processors;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.server.XseDocumentModel;

public interface Processor {
    void process(XseDocumentModel document, XseDocumentModel.Line line, CommandLine commandLine);
}
