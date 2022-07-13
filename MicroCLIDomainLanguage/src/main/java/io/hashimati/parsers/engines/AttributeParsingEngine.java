package io.hashimati.parsers.engines;

import io.hashimati.exceptions.InvalidSyntaxException;
import io.hashimati.parsers.keywords.Keywords;
import io.hashimati.parsers.patterns.GrammarPatterns;
import io.hashimati.syntax.AttributeDeclarationSyntax;
import io.hashimati.utils.PatternUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                 if(!Keywords.DATA_TYPE_KEYWORDS.contains(type)){

                     throw new InvalidSyntaxException(type +" is not a valid data type");

                 }


                 if(hasConstraints)
                 {
                     attributeDeclarationSyntax.getConstraints().addAll(Set.of(declarations.get(0).replace(declaration,"")
                                     .replace(",", "")
                                     .replace(";", "")
                                     .trim().split(" "))
                     );
                    Set<String> invalidValidation =  attributeDeclarationSyntax.getConstraints().stream().filter(x->{

                         boolean firstInspection= Keywords.VALIDATION_KEYWORDS.contains(x);

                         if(firstInspection)
                         {
                             return false;
                         }
                         else{
                             if(x.matches(GrammarPatterns.VALIDATION_MAX) ||
                                     x.matches(GrammarPatterns.VALIDATION_MIN) ||
                                     x.matches(GrammarPatterns.VALIDATION_SIZE)){
                                 return false;
                             }
                             return true;

                         }
                     }).collect(Collectors.toSet());

                    if(invalidValidation.size() >0)
                     throw new InvalidSyntaxException("Invalid validation "+invalidValidation);



                 }


            }
        }
        catch (InvalidSyntaxException ex)
        {
            ex.printStackTrace();;
            attributeDeclarationSyntax.getErrors().add(ex.getMessage());


        }

    }

}
