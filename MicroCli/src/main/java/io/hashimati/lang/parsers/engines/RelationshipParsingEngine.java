package io.hashimati.lang.parsers.engines;

import io.hashimati.lang.exceptions.InvalidSyntaxException;
import io.hashimati.lang.parsers.patterns.GrammarPatterns;
import io.hashimati.lang.syntax.RelationshipSyntax;
import io.hashimati.lang.utils.PatternUtils;

import java.util.List;

public class RelationshipParsingEngine extends ParsingEngine<RelationshipSyntax> {

    @Override
    public RelationshipSyntax parse(String sentence) {
        RelationshipSyntax relationshipSyntax = new RelationshipSyntax(sentence);

        try{
            if(!sentence.matches(GrammarPatterns.relationShipSyntax))
                throw new InvalidSyntaxException(sentence+ ": is not a valid relation declaration");
            getEL(relationshipSyntax);
            return relationshipSyntax;
        }
        catch (InvalidSyntaxException ex)
        {

            relationshipSyntax.setValid(false);

        }

        return relationshipSyntax;
    }

    private void getEL(RelationshipSyntax relationshipSyntax) {
    try {
        if (relationshipSyntax.getType() == null)
            getType(relationshipSyntax);
        String trimSyntax = relationshipSyntax.getSentence().trim();
        List<String> es = PatternUtils.getPatternsFromText("\\w+\\s*\\(\\w+\\)", trimSyntax);

        if(es.size()!=2) throw new InvalidSyntaxException(relationshipSyntax.getSentence()+": is not valid relationship declaration");

        //e1
        String e1Decalation = es.get(0);
        String e1 = e1Decalation.replaceAll("\\(\\w+\\)", "").trim();
        String l1 = e1Decalation.substring(e1Decalation.indexOf('(')).replace("(", "")
                .replace(")", "").trim();

        //e2

        String e2Decalation = es.get(1);
        String e2 = e2Decalation.replaceAll("\\(\\w+\\)", "").trim();
        String l2 = e2Decalation.substring(e2Decalation.indexOf('(')).replace("(", "")
                .replace(")", "").trim();
        relationshipSyntax.setE1(e1);
        relationshipSyntax.setE2(e2);
        relationshipSyntax.setL1(l1);
        relationshipSyntax.setL2(l2);
    }
    catch (InvalidSyntaxException ex)
    {

    }

    }

    private void getType(RelationshipSyntax relationshipSyntax) {
        if (relationshipSyntax.getSentence().trim()
                .startsWith("OneToOne"))
            relationshipSyntax.setType("OneToOne");
        else if (relationshipSyntax.getSentence().trim()
                .startsWith("OneToMany"))
            relationshipSyntax.setType("OneToMany");
        else if (relationshipSyntax.getSentence().trim()
                .startsWith("ManyToMany"))
            relationshipSyntax.setType("ManyToMany");
    }
}
