package io.hashimati.parsers.engines;

import io.hashimati.exceptions.InvalidSyntaxException;
import io.hashimati.parsers.keywords.Keywords;
import io.hashimati.parsers.patterns.GrammarPatterns;
import io.hashimati.syntax.AttributeDeclarationSyntax;
import io.hashimati.utils.PatternUtils;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import static io.hashimati.parsers.keywords.Keywords.VALIDATION_KEYWORDS;

public class AttributeParsingEngine extends ParsingEngine<AttributeDeclarationSyntax> {
    @Override
    public AttributeDeclarationSyntax parse(String sentence) {
        AttributeDeclarationSyntax attributeDeclarationSyntax = new AttributeDeclarationSyntax(sentence);


        auxParsing(attributeDeclarationSyntax);
        return attributeDeclarationSyntax;
    }

    private void auxParsing(AttributeDeclarationSyntax attributeDeclarationSyntax){
        try {
            List<String> attributeDeclaration = PatternUtils.getPatternsFromText(GrammarPatterns.ATTRIBUTE_DECLARATION_PART, attributeDeclarationSyntax.getSentence());

            if(attributeDeclaration.isEmpty())
            {
                throw new InvalidSyntaxException("There is no declaration statement in "+ attributeDeclarationSyntax.getSentence());

            }
            else{
                String declaration = attributeDeclaration.get(0);
                if(!attributeDeclarationSyntax.getSentence().startsWith(declaration)){
                    throw new InvalidSyntaxException("Invalid declaration statement : "+ attributeDeclarationSyntax.getSentence());
                }
                else {
                    String[] nameType = declaration.trim().split(":");

                    if(nameType.length !=2){
                        throw new InvalidSyntaxException("Invalid declaration statement : "+ attributeDeclarationSyntax.getSentence());
                    }


                    if(!Keywords.DATA_TYPE_KEYWORDS.contains(nameType[1].trim()))
                    {
                        throw new InvalidSyntaxException("Cannot recognize type \""+nameType[1].trim()+"\" in ("+attributeDeclarationSyntax.getSentence()+")");
                    }
                    attributeDeclarationSyntax.setName(nameType[0].trim());

                    attributeDeclarationSyntax.setType(nameType[1].trim());


                    for (String p : VALIDATION_KEYWORDS) {
                        var validation = PatternUtils.getPatternsFromText(p, attributeDeclarationSyntax.getSentence());

                        if(validation.isEmpty())
                            continue;
                        if (validation.size() > 1) {
                            throw new InvalidSyntaxException("The " + validation.get(0) + " is defined more than once");
                        }
                        attributeDeclarationSyntax.getConstraints().add(validation.get(0));
                    }

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
