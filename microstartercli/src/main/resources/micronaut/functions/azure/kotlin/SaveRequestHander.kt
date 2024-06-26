package com.example;

import com.microsoft.azure.functions.HttpStatus
import com.microsoft.azure.functions.ExecutionContext
import com.microsoft.azure.functions.HttpRequestMessage
import com.microsoft.azure.functions.HttpMethod
import com.microsoft.azure.functions.HttpResponseMessage
import com.microsoft.azure.functions.annotation.AuthorizationLevel
import com.microsoft.azure.functions.annotation.FunctionName
import com.microsoft.azure.functions.annotation.HttpTrigger
import io.micronaut.azure.function.AzureFunction
import java.util.Optional

class Function : AzureFunction() {
    @FunctionName("HttpTrigger")
    fun hello(
        @HttpTrigger(
            name = "req",
            methods = [HttpMethod.GET],
            route = "{*route}",
            authLevel = AuthorizationLevel.ANONYMOUS
        ) request: HttpRequestMessage<Optional<String?>?>,
        context: ExecutionContext?
    ): HttpResponseMessage {
        context?.logger?.info("Executing Function")
        return request.createResponseBuilder(HttpStatus.OK).body("Hello World").build()
    }
}