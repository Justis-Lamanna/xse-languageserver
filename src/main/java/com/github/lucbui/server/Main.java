package com.github.lucbui.server;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        XseLanguageServer server = new XseLanguageServer();
        if(args.length == 0){
            System.out.println("Starting LSP connected to System IO");
            Launcher<LanguageClient> launcher = LSPLauncher.createServerLauncher(server, System.in, System.out);
            Future<?> startListening = launcher.startListening();

            server.connect(launcher.getRemoteProxy());

            System.out.println("Listening");
            //Loop Forever
            startListening.get();
        }
        else if(args.length == 1) {
            int port = Integer.parseInt(args[0]);
            try(ServerSocket serverSocket = new ServerSocket(port)){
                System.out.println("Starting LSP connected to port " + port);
                Socket socket = serverSocket.accept();
                Launcher<LanguageClient> launcher =
                        LSPLauncher.createServerLauncher(server, socket.getInputStream(), socket.getOutputStream());
                Future<?> startListening = launcher.startListening();

                server.connect(launcher.getRemoteProxy());

                System.out.println("Listening");
                //Loop Forever
                startListening.get();
            }
        } else {
            System.out.println("Arguments: [port number]");
            System.exit(1);
        }
        System.out.println("Exiting...");
    }
}
