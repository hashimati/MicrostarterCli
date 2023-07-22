package io.hashimati.commands;


import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "ssl", description = "To generate certificate using \"keytool\" or \"openssl\"")
public class SSLCommand implements Callable<Integer>{


    @Override
    public Integer call() throws Exception {
        return 0;

    }
}
