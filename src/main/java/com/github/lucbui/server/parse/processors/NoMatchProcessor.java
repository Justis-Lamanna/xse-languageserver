package com.github.lucbui.server.parse.processors;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.server.XseDocumentModel;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

public class NoMatchProcessor implements Processor {
    public static final NoMatchProcessor INSTANCE = new NoMatchProcessor();

    @Override
    public void process(XseDocumentModel document, int linenumber, XseDocumentModel.Line line, CommandLine commandLine) {
        Range range = new Range(new Position(linenumber, 0), new Position(linenumber, line.getLine().length()));
        document.addDiagnostic(linenumber, new Diagnostic(range, "Unknown Command", DiagnosticSeverity.Error, "xse"));
    }

    @Override
    public void deprocess(XseDocumentModel document, int linenumber, XseDocumentModel.Line line, CommandLine commandLine) {
        document.clearDiagnostics(linenumber);
    }
}
