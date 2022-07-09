package io.hashimati.parsers;

import io.hashimati.objects.Entity;
import io.hashimati.parsers.patterns.GrammarPatterns;

public class EntityParser {

    public Entity parseEntity(String entityAsString) {
        String entityName = getEntityName(entityAsString);

        String entityBody = getEntityBody(entityAsString);
        System.out.println(entityBody);
        Entity entity = new Entity();
        entity.setName(entityName);
        System.out.println("Collection:" + getCollectionName(entityAsString));
        entity.setCollectionName(getCollectionName(entityAsString));
        return entity;
    }

    private String getEntityName(String entityAsString) {
        return entityAsString.replaceAll("\\s*\\[\\w+\\]\\s*\\{[\\w\\s]*\\}", "")
                .replaceFirst("\\s*entity\\s*", "").trim();
    }
    private String getCollectionName(String entityAsString)
    {
        return entityAsString.replaceFirst("\\s*entity\\s*\\w+\\s*", "").trim().
                replace(getEntityBody(entityAsString), "")
                .replace("[", "")
                .replace("]", "")
                .trim();

    }

    private String getEntityBody(String entityAsString)
    {
        return entityAsString.replaceFirst("\\s*entity\\s*\\w+\\s*\\[\\w+\\]\\s*", "");
    }

    public boolean isValidEntitySyntax(String entitySyntax)
    {
        return entitySyntax.matches(GrammarPatterns.ENTITY_PATTERN);
    }

    public boolean isValidAttributeLine(String line)
    {
        return line.matches(GrammarPatterns.VALIDATE_LINE_PATTERN);
    }

}
