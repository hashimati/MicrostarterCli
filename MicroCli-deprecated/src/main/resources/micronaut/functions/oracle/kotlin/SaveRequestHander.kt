package com.example

import io.micronaut.core.annotation.ReflectiveAccess
import io.micronaut.oraclecloud.core.TenancyIdProvider
import io.micronaut.oraclecloud.function.OciFunction
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class Function : OciFunction() {
    @Inject
    lateinit var tenantIdProvider : TenancyIdProvider

    @ReflectiveAccess
    fun handleRequest() : String {
        val tenancyId = tenantIdProvider.tenancyId
        return "Your tenancy is: $tenancyId"
    }
}
