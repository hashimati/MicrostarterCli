package io.hashimati.lang.parsers.engines;

import io.hashimati.lang.exceptions.InvalidSyntaxException;
import io.hashimati.lang.syntax.SecuritySyntax;
import io.hashimati.lang.utils.PatternUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityParsingEngine extends ParsingEngine{

    @Override
    public SecuritySyntax parse(String sentence) {
        SecuritySyntax securitySyntax = new SecuritySyntax(sentence);
        parseSecurityType(securitySyntax);
        parseRoles(securitySyntax);
        setPropagate(securitySyntax);
        if(securitySyntax.getType().equalsIgnoreCase("JWT"))
            parseServices(securitySyntax);
        return securitySyntax;
    }

    private void parseRoles(SecuritySyntax securitySyntax) {
        try{
            List<String> roleslist = PatternUtils.getPatternsFromText("\\s*roles\\s+[\\w\\,\\s*]*\\;", securitySyntax.getSentence());

            if(roleslist.isEmpty())
                return;
            if(roleslist.size() > 1)
            {
                throw new InvalidSyntaxException("There are more than one type command");
            }
             String roles = roleslist.get(0).replace("roles", "").replace(";", "").trim();
            securitySyntax.getRoles().addAll(Arrays.stream(roles.split(",")).map(
                    x->x.trim()
            ).collect(Collectors.toList()));
        }
        catch (InvalidSyntaxException ex)
        {

        }
    }


    private void parseServices(SecuritySyntax securitySyntax) {
        try{
            List<String> servicesList = PatternUtils.getPatternsFromText("\\s*services\\s+[\\w\\,\\s*]*\\;", securitySyntax.getSentence());

            if(servicesList.isEmpty())
                return;
            if(servicesList.size() > 1)
            {
                throw new InvalidSyntaxException("There are more than one type command");
            }
            String roles = servicesList.get(0).replace("roles", "").replace(";", "").trim();
            securitySyntax.getRoles().addAll(Arrays.stream(roles.split(",")).map(
                    x->x.trim()
            ).collect(Collectors.toList()));
        }
        catch (InvalidSyntaxException ex)
        {

        }
    }


    private void setPropagate(SecuritySyntax securitySyntax) {
        try {
            List<String> propagateLinePattern = PatternUtils.getPatternsFromText("\\s*propagate\\s*\\;", securitySyntax.getSentence());

            if(propagateLinePattern.isEmpty())
            {
                securitySyntax.setPropagate(false);

            }
            else if(propagateLinePattern.size() > 1){
                throw new InvalidSyntaxException("There are more than one propagate statement. ("+propagateLinePattern+")");
            }
            else {

                securitySyntax.setPropagate(true);
            }

        }catch (InvalidSyntaxException ex)
        {
            ex.printStackTrace();
            securitySyntax.getErrors().add(ex.getMessage());


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
