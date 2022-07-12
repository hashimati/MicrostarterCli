package io.hashimati.parsers.engines;

import io.hashimati.exceptions.InvalidSyntaxException;
import io.hashimati.parsers.patterns.GrammarPatterns;
import io.hashimati.syntax.AttributeDeclarationSyntax;
import io.hashimati.utils.PatternUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class AttributeParsingEngine extends ParsingEngine<AttributeDeclarationSyntax> {
    @Override
    public AttributeDeclarationSyntax parse(String sentence) {
        AttributeDeclarationSyntax attributeDeclarationSyntax = new AttributeDeclarationSyntax(sentence);


        auxParsing(attributeDeclarationSyntax);
        return attributeDeclarationSyntax;
    }

    private void auxParsing(AttributeDeclarationSyntax attributeDeclarationSyntax){

        try {
            List<String> declarations = PatternUtils.getPatternsFromText("\\s*\\w+\\s*:\\s*\\w+[\\s* ; \\,]{1}", attributeDeclarationSyntax.getSentence());

            if(declarations.size() != 1){
                throw new InvalidSyntaxException("Invalid attribute declaration");

            }
            else{


                 String declaration = declarations.get(0);
                boolean hasConstraints = declaration.endsWith(";") || declaration.endsWith(",");

                declaration = declaration.replace(";","")
                         .replace(",", "")
                         .trim();
                 String[] splittings = declaration.split(":");
                 String name = splittings[0];
                 String type = splittings[1];
                 attributeDeclarationSyntax.setName(name);
                 attributeDeclarationSyntax.setType(type);


                 if(hasConstraints)
                 {
                     attributeDeclarationSyntax.getConstraints().addAll(Set.of(declarations.get(0).replace(declaration,"")
                                     .replace(",", "")
                                     .replace(";", "")
                                     .trim().split(" "))
                     );


                 }


            }
        }
        catch (InvalidSyntaxException ex)
        {
            ex.printStackTrace();;

        }

    }

}
