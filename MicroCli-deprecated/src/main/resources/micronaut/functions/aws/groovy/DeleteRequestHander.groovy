package ${pack}
import io.micronaut.core.annotation.Introspected
import io.micronaut.function.aws.MicronautRequestHandler
import javax.inject.Inject

import ${inputImport}


@Introspected
class ${Input}SaveRequestHandler extends MicronautRequestHandler<${Input}, Boolean> {

    @Inject
    private ${Input}Service ${Input.toLowerCase()}Service

    @Override
    Boolean execute(${Input} input) {
        return ${Input.toLowerCase()}Service.deleteById(input)${block}
    }

}
