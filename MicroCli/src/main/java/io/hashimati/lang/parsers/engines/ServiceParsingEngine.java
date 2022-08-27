package io.hashimati.lang.parsers.engines;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
import io.hashimati.lang.exceptions.InvalidSyntaxException;
import io.hashimati.lang.parsers.patterns.GrammarPatterns;
import io.hashimati.lang.syntax.ServiceSyntax;
import io.hashimati.lang.utils.PatternUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceParsingEngine extends ParsingEngine{
    @Override
    public ServiceSyntax parse(final String sentence) {

        ServiceSyntax serviceSyntax = new ServiceSyntax(sentence);
        getServiceName(serviceSyntax);
        getPort(serviceSyntax);
        serviceSyntax.setPackage(getAttribute(serviceSyntax, "package"));
        serviceSyntax.setReactive(getAttribute(serviceSyntax, "reactive"));
        serviceSyntax.setBuild(getAttribute(serviceSyntax,"build"));
        serviceSyntax.setCache(getAttribute(serviceSyntax,"cache"));
        serviceSyntax.setDatabase(getAttribute(serviceSyntax,"database"));
        serviceSyntax.setFile(getAttribute(serviceSyntax,"file"));
        serviceSyntax.setAwsKey(getAttribute(serviceSyntax, "awsKey"));
        serviceSyntax.setAwsSecret(getAttribute(serviceSyntax, "awsSecret"));
        serviceSyntax.setLanguage(getAttribute(serviceSyntax, "language"));
        serviceSyntax.setMessaging(getAttribute(serviceSyntax,"messaging"));
        serviceSyntax.setDatabaseName(getAttribute(serviceSyntax,"databaseName"));
        serviceSyntax.setMetrics(getAttribute(serviceSyntax,"metrics"));
        serviceSyntax.setAnnotation(getAttribute(serviceSyntax,"annotation"));
        serviceSyntax.setTracing(getAttribute(serviceSyntax,"tracing"));
        serviceSyntax.setFramework(getAttribute(serviceSyntax,"framework"));
        serviceSyntax.setFramework(getAttribute(serviceSyntax,"testFramework"));
        serviceSyntax.setDao(getAttribute(serviceSyntax,"dao"));
        serviceSyntax.setMigrationTool(getAttribute(serviceSyntax, "migrationTool"));




        getGraphQl(serviceSyntax);
        getEnums(serviceSyntax);
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
            List<String> commandPattern = PatternUtils.getPatternsFromText("\\s*"+command+"\\s+[\\w+\\.]+\\s*;", serviceSyntax.getSentence());

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


    private void getRelationships(ServiceSyntax serviceSyntax)
    {

        RelationshipParsingEngine relationshipParsingEngine = new RelationshipParsingEngine();
        List<String> relationshipStatements = PatternUtils.getPatternsFromText(GrammarPatterns.relationShipSyntax, serviceSyntax.getSentence()
                .replaceAll("\\s*[^\\w]*entity\\s+\\w+\\s*\\{[\\s*\\/\\w*\\:\\;\\-\\(\\)]*}\\s*", "")
                .replaceAll("\\s*[^\\w]*enum\\s+\\w+\\s*\\{[\\s*\\/\\w*\\:\\;\\,\\-\\(\\)]*}\\s*", "")
        );
        if(relationshipStatements != null && !relationshipStatements.isEmpty()) {
            serviceSyntax.getRelationships().addAll(
                    relationshipStatements
                            .stream()
                            .map(x->relationshipParsingEngine.parse(x))
                            .collect(Collectors.toList()
                            )
            );
        }

    }
    private void getEntities(ServiceSyntax serviceSyntax)
    {
        EntityParsingEngine entityParsingEngine = new EntityParsingEngine();
       // List<String> entityStatements = PatternUtils.getPatternsFromText("\\s*entity\\s+\\w+\\s*\\{[\\;\\!\\@\\#\\$\\%\\^\\&\\*\\:\\w*\\s*^\\}\\(\\)\\-]*\\}\\s*^(\\w*)", serviceSyntax.getSentence());
        List<String> entityStatements = PatternUtils.getPatternsFromText("\\s*[^\\w]*entity\\s+\\w+\\s*\\{[\\s*\\/\\w*\\:\\;\\-\\(\\)]*}\\s*", serviceSyntax.getSentence());
        if(entityStatements.isEmpty())
        {
            return ;
        }
        else {
            serviceSyntax.getEntities().addAll(entityStatements.stream().map(x->entityParsingEngine.parse(x // to close the entity declaration bracket

            )).collect(Collectors.toList()));

        }
    }
    private void getEnums(ServiceSyntax serviceSyntax) {
        EnumParsingEngine enumParsingEngine = new EnumParsingEngine();
        // List<String> entityStatements = PatternUtils.getPatternsFromText("\\s*entity\\s+\\w+\\s*\\{[\\;\\!\\@\\#\\$\\%\\^\\&\\*\\:\\w*\\s*^\\}\\(\\)\\-]*\\}\\s*^(\\w*)", serviceSyntax.getSentence());
        List<String> enumStatements = PatternUtils.getPatternsFromText("\\s*[^\\w]*enum\\s+\\w+\\s*\\{[\\s*\\/\\w*\\:\\;\\,\\-\\(\\)]*}\\s*", serviceSyntax.getSentence());
        if(enumStatements.isEmpty())
        {
            return ;
        }
        else {
            serviceSyntax.getEnums().addAll(enumStatements.stream().map(x->enumParsingEngine.parse(x // to close the entity declaration bracket

            )).collect(Collectors.toList()));

        }
    }
}
