package io.hashimati.microcli.config;
//
//
//import io.micronaut.context.annotation.Factory;
//import org.jline.reader.LineReader;
//import org.jline.reader.LineReaderBuilder;
//import org.jline.terminal.Terminal;
//import org.jline.terminal.TerminalBuilder;
//
//import javax.inject.Singleton;
//import java.io.IOException;
//
//@Factory
//public class JLineFactory {
//
//
//
//    @Singleton
//    public Terminal terminal() throws IOException {
//
//        return TerminalBuilder
//                .builder()
//                .system(true)
//                .streams(System.in, System.out)
//                .build();
//    }
//    @Singleton
//    public LineReader lineReader () throws IOException {
//
//        return LineReaderBuilder.builder()
//                .terminal(this.terminal())
//
//                .build();
//    }
//}
