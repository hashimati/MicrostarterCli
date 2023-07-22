package micronaut.functions.aws.kotlin

import io.micronaut.core.annotation.Introspected
import io.micronaut.function.aws.MicronautRequestHandler
import ${inputImport}

@Introspected
class ${Input}SaveRequestHandler : MicronautRequestHandler<${Input}?, ${Output}?>(private val ${Input}.toLowerCase()}Service : ${Input}Service ) {
    override fun execute(input: ${Input}?): ${Output}? {
        return if (input != null) {
            return ${Input.toLowerCase()}Service.save(input)
        } else {
            null
        }
    }
}

