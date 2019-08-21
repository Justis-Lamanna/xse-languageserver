package com.github.lucbui.server;

import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.services.*;

import java.util.concurrent.CompletableFuture;

public class XseLanguageServer implements LanguageServer, LanguageClientAware {

    private final XseWorkspaceService workspaceService;
    private final XseTextDocumentService textDocumentService;

    private LanguageClient remoteProxy;

    public XseLanguageServer() {
        textDocumentService = new XseTextDocumentService(this);
        workspaceService = new XseWorkspaceService();
    }

    @Override
    public CompletableFuture<InitializeResult> initialize(InitializeParams initializeParams) {
        InitializeResult result = new InitializeResult(new ServerCapabilities());

        //result.getCapabilities().setCodeActionProvider(Boolean.TRUE);
        //result.getCapabilities().setCompletionProvider(new CompletionOptions());
        //result.getCapabilities().setDefinitionProvider(Boolean.TRUE);
        //result.getCapabilities().setHoverProvider(Boolean.TRUE);
        //result.getCapabilities().setReferencesProvider(Boolean.TRUE);
        result.getCapabilities().setTextDocumentSync(TextDocumentSyncKind.Incremental);
        //result.getCapabilities().setDocumentSymbolProvider(Boolean.TRUE);

        return CompletableFuture.supplyAsync(() -> result);
    }

    @Override
    public CompletableFuture<Object> shutdown() {
        return CompletableFuture.supplyAsync(() -> Boolean.TRUE);
    }

    @Override
    public void exit() {

    }

    @Override
    public TextDocumentService getTextDocumentService() {
        return textDocumentService;
    }

    @Override
    public WorkspaceService getWorkspaceService() {
        return workspaceService;
    }

    public LanguageClient getRemoteProxy() {
        return remoteProxy;
    }

    @Override
    public void connect(LanguageClient languageClient) {
        this.remoteProxy = languageClient;
    }
}
