package ${pack};
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import javax.inject.Inject;
import java.lang.Iterable
import ${inputImport};
@Introspected
public class ${Input}SaveRequestHandler extends MicronautRequestHandler<String, Iterable<${Output}>) {

    @Inject
    private ${Output}Service ${Output.toLowerCase()}Service;

    @Override
    public Iterable<${Output}> execute(String input) {
        return ${Input.toLowerCase()}Service.findAll();
    }

}
