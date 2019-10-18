package com.github.lucbui.server.parse.processors;

import com.github.lucbui.line.CommandLine;
import com.github.lucbui.server.XseDocumentModel;
import com.github.lucbui.util.CommandUtils;
import com.github.lucbui.util.ProcessorUtils;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Range;

import java.util.List;
import java.util.OptionalLong;

public class DefineProcessor implements Processor {
    @Override
    public void process(XseDocumentModel document, int linenumber, XseDocumentModel.Line line, CommandLine commandLine) {
        String[] params = commandLine.getParameters();
        String name = params[0];
        OptionalLong value = CommandUtils.parseLong(params[1]);
        if(value.isPresent()){
            document.clearDiagnostics(linenumber);
        } else {
            List<Range> ranges = ProcessorUtils.getRangesForWords(linenumber, line.getLine());
            Diagnostic diagnostic = new Diagnostic(ranges.get(2), "Value must be numeric", DiagnosticSeverity.Error, "xse");
            document.addDiagnostic(linenumber, diagnostic);
        }
    }

    @Override
    public void deprocess(XseDocumentModel document, int linenumber, XseDocumentModel.Line line, CommandLine commandLine) {
        document.clearDiagnostics(linenumber);
    }
}
