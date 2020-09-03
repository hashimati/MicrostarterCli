package io.hashimati.microcli.commands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name= "create-websocket-server", aliases = {"websocketServer", "websocket-server"}, description = "To create websocket server component")
public class CreateWebsocketServerCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
