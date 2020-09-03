package io.hashimati.microcli.commands;

import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name= "create-websocket-client", aliases = {"websocketClient", "websocket-client"}, description = "To create websocket client component")
public class CreateWebsocketClientCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return 0;
    }
}
