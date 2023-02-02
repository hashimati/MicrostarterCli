package io.hashimati.microcli.commands.spring;


import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "create-entity", aliases ={"entity, e"}, description = "Create a Spring Entity")
public class CreateEntityCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {

        return 0;
    }
}
