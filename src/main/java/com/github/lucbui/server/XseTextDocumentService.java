package com.github.lucbui.server;

import com.github.lucbui.util.Pair;
import org.eclipse.lsp4j.*;
import org.eclipse.lsp4j.services.TextDocumentService;

import java.util.*;
import java.util.concurrent.CompletableFuture;

public class XseTextDocumentService implements TextDocumentService {

    private final XseLanguageServer xseLanguageServer;
    private final Map<String, XseDocumentModel> documents = Collections.synchronizedMap(new HashMap<>());

    public XseTextDocumentService(XseLanguageServer xseLanguageServer) {
        this.xseLanguageServer = xseLanguageServer;
    }

    @Override
    public void didOpen(DidOpenTextDocumentParams didOpenTextDocumentParams) {
        String uri = didOpenTextDocumentParams.getTextDocument().getUri();
        XseDocumentModel model = new XseDocumentModel(didOpenTextDocumentParams.getTextDocument().getText());
        this.documents.put(uri, model);

        CompletableFuture.runAsync(() -> {
            xseLanguageServer.getRemoteProxy()
                    .publishDiagnostics(new PublishDiagnosticsParams(uri, runDiagnosticsOn(model)));
        });
    }

    @Override
    public void didChange(DidChangeTextDocumentParams didChangeTextDocumentParams) {
        String uri = didChangeTextDocumentParams.getTextDocument().getUri();
        List<TextDocumentContentChangeEvent> changes = didChangeTextDocumentParams.getContentChanges();
        XseDocumentModel model;
        if(!changes.isEmpty()){
            if(changes.get(0).getRange() == null){
                //If range is null, the full text is being passed.
                model = new XseDocumentModel(changes.get(0).getText());
                this.documents.put(uri, model);
            } else {
                //Having a range means we are viewing incremental changes.
                XseDocumentModel oldDocumentModel = this.documents.get(didChangeTextDocumentParams.getTextDocument().getUri());
                oldDocumentModel.applyChanges(changes);
                //TODO: Apply changes to model
                model = oldDocumentModel;
            }

            CompletableFuture.runAsync(() -> {
                xseLanguageServer.getRemoteProxy()
                    .publishDiagnostics(new PublishDiagnosticsParams(uri, runDiagnosticsOn(model)));
            });
        }
    }

    private List<Diagnostic> runDiagnosticsOn(XseDocumentModel model){
        List<Diagnostic> diagnostics = new ArrayList<>();
        for(Pair<Integer, XseDocumentModel.Line> line : model.lines()){
            if(line.getValue().getLine().equals("...")){
                Range range = new Range(new Position(line.getKey(), 0), new Position(line.getKey(), 3));
                Diagnostic diagnostic = new Diagnostic(range, "This is an ellipsis, you know.", DiagnosticSeverity.Error, "xse");
                diagnostics.add(diagnostic);
            }
        }
        return diagnostics;
    }

    @Override
    public void didClose(DidCloseTextDocumentParams didCloseTextDocumentParams) {
        this.documents.remove(didCloseTextDocumentParams.getTextDocument().getUri());
    }

    @Override
    public void didSave(DidSaveTextDocumentParams didSaveTextDocumentParams) {
        //Nothing necessary
    }
}
