package com.example;

import io.micronaut.core.annotation.ReflectiveAccess;
import io.micronaut.oraclecloud.function.OciFunction;
import io.micronaut.oraclecloud.core.TenancyIdProvider;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;


@Singleton
public class Function extends OciFunction {

    @Inject
    TenancyIdProvider tenantIdProvider;

    @ReflectiveAccess
    public String handleRequest() {
        String tenancyId = tenantIdProvider.getTenancyId();
        return "Your tenancy is: " + tenancyId;\
    }
}