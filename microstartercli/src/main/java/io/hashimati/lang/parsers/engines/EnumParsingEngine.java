package io.hashimati.lang.parsers.engines;

import io.hashimati.lang.exceptions.InvalidSyntaxException;
import io.hashimati.lang.syntax.EnumSyntax;
import io.hashimati.lang.utils.PatternUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EnumParsingEngine extends ParsingEngine{
    @Override
    public EnumSyntax parse(String sentence) {

        EnumSyntax enumSyntax = new EnumSyntax(sentence);
        getName(enumSyntax);
        getEnums(enumSyntax);
        return enumSyntax;
    }

    private void getName(EnumSyntax enumSyntax)
    {
        try {
            List<String> entityNameDeclarationLine = PatternUtils.getPatternsFromText("\\s*enum\\s+\\w*\\s*\\{", enumSyntax.getSentence());
            if (entityNameDeclarationLine.size() != 1) {
                throw new InvalidSyntaxException("Entity's name cannot be found");
            }
            else{
                enumSyntax.setName(Optional.ofNullable(entityNameDeclarationLine.get(0)
                                .replaceAll("\\s*entity \\s*", "")
                                .replaceAll("\\s*\\{","" ))
                        .map(x->{return x.trim().isEmpty()?null:x.trim();})
                        .orElse(null));
                enumSyntax.setValid(enumSyntax.getName() != null);

                //  Keywords.DATA_TYPE_KEYWORDS.add(entitySyntax.getName());
            }
        }
        catch (InvalidSyntaxException ex)
        {
            ex.printStackTrace();
            enumSyntax.setValid(false);
            enumSyntax.getErrors().add(ex.getMessage());
        }
    }

    private void getEnums(EnumSyntax enumSyntax)
    {

        try {
            enumSyntax.getEnums().addAll(Arrays.asList(
                    enumSyntax.getSentence().replaceAll("\\s*enum\\s+\\w*\\s*\\{", "")
                            .replace("}", "")
                            .strip()
                            .split(",")
            ));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            enumSyntax.setValid(false);
            enumSyntax.getErrors().add(ex.getMessage());
        }
    }
}
