package io.hashimati.utils;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class MicronautProjectValidatorTest {

    @Test
    void appendToXMLNode() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
            MicronautProjectValidator.appendToXMLNode("sdf", "sdf");
        assertEquals(1,1);
    }

}