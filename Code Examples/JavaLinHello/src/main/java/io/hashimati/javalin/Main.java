package io.hashimati.javalin;

import io.javalin.Javalin;

public class Main {

    public static void main(String[] args) {
        Javalin javalin = Javalin.create().start(8080);
        javalin.get("/", ctx -> ctx.result("Hello Javalin"));
    }
}
