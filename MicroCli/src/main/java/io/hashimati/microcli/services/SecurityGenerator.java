package io.hashimati.microcli.services;


import io.hashimati.microcli.domains.ConfigurationInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SecurityGenerator {


    @Inject
    private TemplatesService templatesService;

    private ConfigurationInfo configurationInfo;
}
