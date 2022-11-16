package io.hashimati.lang.parsers.engines;

import io.hashimati.lang.exceptions.InvalidSyntaxException;
import io.hashimati.lang.syntax.SecuritySyntax;
import io.hashimati.lang.syntax.Syntax;
import io.hashimati.lang.utils.PatternUtils;

import java.io.SyncFailedException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityParsingEngine extends ParsingEngine{


    @Override
    public SecuritySyntax parse(String sentence) {
        SecuritySyntax securitySyntax = new SecuritySyntax(sentence);

        parseSecurityType(securitySyntax);

        parseRoles(securitySyntax);
        return null;

    }

    private void parseRoles(SecuritySyntax securitySyntax) {
        try{
            List<String> roleslist = PatternUtils.getPatternsFromText("\\s*roles\\s+(\\s?([\\w\\s]*|\\d)\\s?(,|$)){16}\\;", securitySyntax.getSentence());
            if(roleslist.isEmpty())
                return;
            if(roleslist.size() > 1)
            {
                throw new InvalidSyntaxException("There are more than one type command");

            }
             String roles = roleslist.get(0).replace("roles", "").trim();
            securitySyntax.getRoles().addAll(Arrays.stream(roles.split(",")).map(
                    x->x.trim()
            ).collect(Collectors.toList())
            );
        }
        catch (InvalidSyntaxException ex)
        {

        }
    }

    public void parseSecurityType(SecuritySyntax securitySyntax)
    {
        try{
            List<String> typelist = PatternUtils.getPatternsFromText("\\s*type\\s+\\w*\\s*\\;", securitySyntax.getSentence());
            if(typelist.isEmpty())
                throw new InvalidSyntaxException("The security type is not specified!");
            if(typelist.size() > 1)
            {
                throw new InvalidSyntaxException("There are more than one type command");

            }
            securitySyntax.setType( typelist.get(0).replace("type", "").replace(";", "").trim().strip());
        }
        catch (InvalidSyntaxException ex)
        {

        }
    }
}
