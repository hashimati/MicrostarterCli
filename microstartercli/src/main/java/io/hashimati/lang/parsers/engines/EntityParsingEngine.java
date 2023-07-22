package io.hashimati.lang.parsers.engines;

/**
 * @author Ahmed Al Hashmi
 * Github: https://www.github.com/hashimati
 * twitter: @hashimati
 */
import io.hashimati.lang.exceptions.InvalidSyntaxException;
import io.hashimati.lang.parsers.patterns.GrammarPatterns;
import io.hashimati.lang.syntax.EntitySyntax;
import io.hashimati.lang.utils.PatternUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityParsingEngine extends ParsingEngine{

    @Override
    public EntitySyntax parse(String sentence) {
        EntitySyntax entitySyntax = new EntitySyntax(sentence);
        getEntityName(entitySyntax);
        getPagination(entitySyntax);
        getRecord(entitySyntax);
        getAttributesDeclarationStatements(entitySyntax);
        getAttributeDeclarationSyntax(entitySyntax);
        getMicrostreamPath(entitySyntax);
        getNoendpoint(entitySyntax);
        getGraphQL(entitySyntax);
        getGrpc(entitySyntax);
        return entitySyntax;
    }

    private void getEntityName(EntitySyntax entitySyntax)
    {
        try {
            List<String> entityNameDeclarationLine = PatternUtils.getPatternsFromText("\\s*entity\\s+\\w*\\s*\\{", entitySyntax.getSentence());
            if (entityNameDeclarationLine.size() != 1) {
                throw new InvalidSyntaxException("Entity's name cannot be found");
            }
            else{
                 entitySyntax.setName(Optional.ofNullable(entityNameDeclarationLine.get(0)
                            .replaceAll("\\s*entity \\s*", "")
                            .replaceAll("\\s*\\{","" ))
                        .map(x->{return x.trim().isEmpty()?null:x.trim();})
                        .orElse(null));
                 entitySyntax.setValid(entitySyntax.getName() != null);

               //  Keywords.DATA_TYPE_KEYWORDS.add(entitySyntax.getName());
            }
        }
        catch (InvalidSyntaxException ex)
        {
            ex.printStackTrace();
            entitySyntax.setValid(false);
            entitySyntax.getErrors().add(ex.getMessage());
        }
    }
    
    private void getPagination(EntitySyntax entitySyntax){
        try{

            List<String> paginationDeclaration = PatternUtils.getPatternsFromText(GrammarPatterns.PAGINATION_COMMAND_PATTERN,  entitySyntax.getSentence());
            if(paginationDeclaration.size() > 1){
                throw new InvalidSyntaxException("\"pagination;\" exists more than once!");
            }
            if(paginationDeclaration.size() == 0){
                entitySyntax.setPagination(false);
            }
            else {
                entitySyntax.setPagination(true);
            }

        }
        catch (InvalidSyntaxException ex){
            ex.printStackTrace();
            entitySyntax.setValid(false);
            entitySyntax.getErrors().add(ex.getMessage());

        }
    }

    private void getNoendpoint(EntitySyntax entitySyntax)
    {
        try {

            List<String> recordsDeclaration = PatternUtils.getPatternsFromText(GrammarPatterns.NOENDPOINT_COMMAND_PATTERN, entitySyntax.getSentence());
            if (recordsDeclaration.size() > 1) {
                throw new InvalidSyntaxException("\"noendpoints;\" exists more than once!");
            }
            if (recordsDeclaration.size() == 0) {

                entitySyntax.setNoendpoints(false);
            }
            else{
                entitySyntax.setNoendpoints(true);
            }
        }
        catch(InvalidSyntaxException ex)
        {
            entitySyntax.setValid(false);
        }
    }
    private void getRecord(EntitySyntax entitySyntax){
        try{

            List<String> recordsDeclaration = PatternUtils.getPatternsFromText(GrammarPatterns.RECORDS_COMMAND_PATTERN,  entitySyntax.getSentence());
            if(recordsDeclaration.size() > 1){
                throw new InvalidSyntaxException("\"records;\" exists more than once!");
            }
            if(recordsDeclaration.size() == 0){
                entitySyntax.setRecords(false);
            }
            else {
                entitySyntax.setRecords(true);
            }

        }
        catch (InvalidSyntaxException ex){
            ex.printStackTrace();
            entitySyntax.setValid(false);
            entitySyntax.getErrors().add(ex.getMessage());


        }
    }


    private void getGraphQL(EntitySyntax entitySyntax){
        try{

            List<String> recordsDeclaration = PatternUtils.getPatternsFromText(GrammarPatterns.GRAPHQL_COMMAND_PATTERN,  entitySyntax.getSentence());
            if(recordsDeclaration.size() > 1){
                throw new InvalidSyntaxException("\"graphql;\" exists more than once!");
            }
            if(recordsDeclaration.size() == 0){
                entitySyntax.setGraphql(false);
            }
            else {
                entitySyntax.setGraphql(true);
            }

        }
        catch (InvalidSyntaxException ex){
            ex.printStackTrace();
            entitySyntax.setValid(false);
            entitySyntax.getErrors().add(ex.getMessage());


        }
    }

    private void getGrpc(EntitySyntax entitySyntax){
        try{

            List<String> recordsDeclaration = PatternUtils.getPatternsFromText(GrammarPatterns.GRPC_COMMAND_PATTERN,  entitySyntax.getSentence());
            if(recordsDeclaration.size() > 1){
                throw new InvalidSyntaxException("\"grpc;\" exists more than once!");
            }
            if(recordsDeclaration.size() == 0){
                entitySyntax.setGrpc(false);
            }
            else {
                entitySyntax.setGrpc(true);
            }

        }
        catch (InvalidSyntaxException ex){
            ex.printStackTrace();
            entitySyntax.setValid(false);
            entitySyntax.getErrors().add(ex.getMessage());


        }
    }


    private void getNoEndPoints(EntitySyntax entitySyntax){
        try{

            List<String> recordsDeclaration = PatternUtils.getPatternsFromText(GrammarPatterns.NOENDPOINT_COMMAND_PATTERN,  entitySyntax.getSentence());
            if(recordsDeclaration.size() > 1){
                throw new InvalidSyntaxException("\"records;\" exists more than once!");
            }
            if(recordsDeclaration.size() == 0){
                entitySyntax.setNoendpoints(false);
            }
            else {
                entitySyntax.setNoendpoints(true);
            }

        }
        catch (InvalidSyntaxException ex){
            ex.printStackTrace();
            entitySyntax.setValid(false);
            entitySyntax.getErrors().add(ex.getMessage());


        }
    }

    private void getMicrostreamPath(EntitySyntax entitySyntax)
    {
        try{

            List<String> mPath = PatternUtils.getPatternsFromText(GrammarPatterns.MICROSTREAM_PATH,  entitySyntax.getSentence());
            if(mPath.size() > 1){
                throw new InvalidSyntaxException("\"microstreamPath\" exists more than once!");
            }
            if(mPath.size() == 0){
                entitySyntax.setMicrostreamPath(null);
            }
            else {
                entitySyntax.setMicrostreamPath(mPath.get(0)
                        .replaceAll("microstreamPath", "")
                        .replace(";", "")
                        .trim()
                );
            }
        }
        catch (InvalidSyntaxException ex){
            ex.printStackTrace();
            entitySyntax.setValid(false);
            entitySyntax.getErrors().add(ex.getMessage());


        }
    }
    private void getAttributesDeclarationStatements(EntitySyntax entitySyntax)
    {
            try{
                String body = entitySyntax.getSentence().replaceAll("\\s*entity \\s*\\w*\\s*\\{", "").trim();

                List<String> attributeDeclarations = PatternUtils.getPatternsFromText(GrammarPatterns.FULL_ATTRIBUTE_DECLARATION,  body);
                entitySyntax.getAttributesDeclarationsStr().addAll(attributeDeclarations.stream().map(x->x.trim()).collect(Collectors.toList()));
            }
            catch (Exception ex){
                ex.printStackTrace();
                entitySyntax.setValid(false);

                entitySyntax.getErrors().add(ex.getMessage());
            }
    }
    private void getAttributeDeclarationSyntax(EntitySyntax entitySyntax)
    {
        try{
            if(!entitySyntax.getAttributesDeclarationsStr().isEmpty()){
                entitySyntax.getAttributesDeclarations()
                        .addAll(entitySyntax.getAttributesDeclarationsStr().stream().map(x->new AttributeParsingEngine().parse(x)).collect(Collectors.toList()));
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
            entitySyntax.setValid(false);
            entitySyntax.getErrors().add(ex.getMessage());
        }
    }
}
