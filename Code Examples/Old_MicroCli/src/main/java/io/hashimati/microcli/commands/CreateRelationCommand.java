package io.hashimati.microcli.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;


@Command
class CreateRelationCommand implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        return null;
    }
}
