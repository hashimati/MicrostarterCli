package io.hashimati.microcli.services;

import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.domains.Entity;
import io.hashimati.microcli.domains.EntityAttribute;
import io.hashimati.microcli.exceptions.NotImplementedException;
import io.hashimati.microcli.utils.DataTypeMapper;
import io.micronaut.core.naming.NameUtils;

import java.io.IOException;
import java.util.HashMap;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.GROOVY_LANG;
import static io.hashimati.microcli.services.TemplatesService.FILE_CLIENT_METHODS;

public class JavaScriptGenerator {

    public String generateEntity(Entity entity) {
        throw new NotImplementedException("Not Implemented");
    }

    public String generateClient(Entity entity) throws IOException, ClassNotFoundException {
        throw new NotImplementedException("Not Implemented");
    }

}
