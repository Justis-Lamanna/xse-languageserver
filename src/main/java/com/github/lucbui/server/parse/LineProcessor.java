package com.github.lucbui.server.parse;

import com.github.lucbui.server.XseDocumentModel;

public interface LineProcessor {
    void processLine(XseDocumentModel document, int linenumber, XseDocumentModel.Line line);

    void deprocessLine(XseDocumentModel document, int linenumber, XseDocumentModel.Line line);
}
