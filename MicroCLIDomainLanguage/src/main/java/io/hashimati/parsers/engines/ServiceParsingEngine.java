package io.hashimati.parsers.engines;

import io.hashimati.exceptions.InvalidSyntaxException;
import io.hashimati.parsers.patterns.GrammarPatterns;
import io.hashimati.syntax.ServiceSyntax;
import io.hashimati.syntax.Syntax;
import io.hashimati.utils.PatternUtils;

import javax.crypto.EncryptedPrivateKeyInfo;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceParsingEngine extends ParsingEngine{
    @Override
    public ServiceSyntax parse(final String sentence) {

        ServiceSyntax serviceSyntax = new ServiceSyntax(sentence);
        getServiceName(serviceSyntax);
        getPort(serviceSyntax);
        serviceSyntax.setReactive(getAttribute(serviceSyntax, "reactive"));

        serviceSyntax.setBuild(getAttribute(serviceSyntax,"build"));
        serviceSyntax.setCache(getAttribute(serviceSyntax,"cache"));
        serviceSyntax.setDatabase(getAttribute(serviceSyntax,"database"));
        serviceSyntax.setFile(getAttribute(serviceSyntax,"file"));
        serviceSyntax.setLanguage(getAttribute(serviceSyntax, "language"));
        serviceSyntax.setMessaging(getAttribute(serviceSyntax,"messaging"));
        serviceSyntax.setDatabaseName(getAttribute(serviceSyntax,"databaseName"));
        serviceSyntax.setMetrics(getAttribute(serviceSyntax,"metrics"));
        serviceSyntax.setJaxRS(getAttribute(serviceSyntax,"jaxRX"));
        serviceSyntax.setPackage(getAttribute(serviceSyntax,"package"));
        serviceSyntax.setTracing(getAttribute(serviceSyntax,"tracing"));
        serviceSyntax.setTracing(getAttribute(serviceSyntax,"framework"));
        getGraphQl(serviceSyntax);


        getEntities(serviceSyntax); 
        return serviceSyntax;

    }

    private void getGraphQl(ServiceSyntax serviceSyntax) {
        try {
            List<String> graphqlLinePattern = PatternUtils.getPatternsFromText("\\s*graphql\\s*\\;", serviceSyntax.getSentence());

            if(graphqlLinePattern.isEmpty())
            {
                serviceSyntax.setGraphql(false);

            }
            else if(graphqlLinePattern.size() > 1){
                throw new InvalidSyntaxException("There are more than one graphql statement. ("+graphqlLinePattern+")");
            }
            else {

                    serviceSyntax.setGraphql(true);
            }

        }catch (InvalidSyntaxException ex)
        {
            ex.printStackTrace();
            serviceSyntax.getErrors().add(ex.getMessage());


        }
    }


    private void getServiceName(ServiceSyntax serviceSyntax){
        try {
            List<String> serviceNameDeclarationLine = PatternUtils.getPatternsFromText("\\s*service\\s+\\w*\\s*\\{", serviceSyntax.getSentence());
            if (serviceNameDeclarationLine.size() != 1) {
                throw new InvalidSyntaxException("Entity's name cannot be found");
            }
            else{
                serviceSyntax.setName(Optional.ofNullable(serviceNameDeclarationLine.get(0)
                                .replaceAll("\\s*entity\\s+", "")
                                .replaceAll("\\s*\\{","" ))
                        .map(x->{return x.trim().isEmpty()?null:x.trim();})
                        .orElse(null).split("\\s+")[1]);
                serviceSyntax.setValid(serviceSyntax.getName() != null);

                //  Keywords.DATA_TYPE_KEYWORDS.add(entitySyntax.getName());
            }
        }
        catch (InvalidSyntaxException ex)
        {
            ex.printStackTrace();
            serviceSyntax.setValid(false);
            serviceSyntax.getErrors().add(ex.getMessage());
        }
    }

    private void getPort(ServiceSyntax serviceSyntax){
        try
        {
            List<String> portLinePattern = PatternUtils.getPatternsFromText(GrammarPatterns.PORT_COMMAND_PATTERN, serviceSyntax.getSentence());

            if(portLinePattern.isEmpty())
            {
                serviceSyntax.setPort("8080");

            }
            else if(portLinePattern.size() > 1){
                throw new InvalidSyntaxException("There are more than one port statement. ("+portLinePattern+")");
            }
            else {
                var portStatement = portLinePattern.get(0).trim();
                var port = portStatement.split(" ")[1].replace(";", "").trim();

                if(!port.matches("^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))"))
                    throw new InvalidSyntaxException("The port number is not valid: " + (portStatement));

                serviceSyntax.setPort(port);

            }

        }
        catch (InvalidSyntaxException ex)
        {

            ex.printStackTrace();
            serviceSyntax.getErrors().add(ex.getMessage());
        }

    }

    private String getAttribute(ServiceSyntax serviceSyntax, String command)
    {
        try{
            List<String> commandPattern = PatternUtils.getPatternsFromText("\\s*"+command+"\\s+\\w+\\s*;", serviceSyntax.getSentence());

            if(commandPattern.isEmpty())
            {
             //   serviceSyntax.setReactive(null);
                return null;
            }
            else if (commandPattern.size()>1){
                throw new InvalidSyntaxException("The command "+command+" is defined more than once: " + commandPattern);
            }
            else{
                String[] commandSplit = commandPattern.get(0).trim().split(" ");
                return commandSplit[1].trim().replace(";", "").trim();
            }

        }
        catch (InvalidSyntaxException ex)
        {
            ex.printStackTrace();
            serviceSyntax.getErrors().add(ex.getMessage());
            return null;

        }
    }

    private void getEntities(ServiceSyntax serviceSyntax)
    {
        EntityParsingEngine entityParsingEngine = new EntityParsingEngine();
        List<String> entityStatements = PatternUtils.getPatternsFromText(GrammarPatterns.ENTITY_PATTERN, serviceSyntax.getSentence());
        if(entityStatements.isEmpty())
        {
            return ;
        }
        else {
            serviceSyntax.getEntities().addAll(entityStatements.stream().map(x->entityParsingEngine.parse(x+"\n}" // to close the entity declaration bracket

            )).collect(Collectors.toList()));

        }
    }
}
