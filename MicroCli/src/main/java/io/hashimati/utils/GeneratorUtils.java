package io.hashimati.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import groovy.text.SimpleTemplateEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class GeneratorUtils
{
   static Logger logger = LoggerFactory.getLogger(GeneratorUtils.class);
    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));
        return builder.parse(is);
    }

    public static String xmlToString(Document document) throws ParserConfigurationException, TransformerException {
        DOMSource domSource = new DOMSource(document);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);

        return writer.toString();
    }

    public static String removeDuplicateLines(String snippet){

        return new HashSet<String>(){{
            addAll(Arrays.asList(snippet.split("\n")));
        }}.stream().sorted((x,y)->x.compareTo(y)).reduce("", (x,y)->x + "\n" +y );
    }


    //writing String block to a file;
    public static boolean createFile(String path, String content) throws IOException {

        File file = new File( path);
        File parent = file.getParentFile();
        parent.setWritable(true);
        if(!parent.exists())
            parent.mkdirs();
        //file.createNewFile();
        logger.info("creating {}", path);
        try  {
            PrintWriter pw = new PrintWriter(file);
            pw.println(content);
            pw.flush();
            pw.close();
            logger.info("created {}", path
            );


            return true;
        } catch (FileNotFoundException e) {
            logger.error("Failed to crate {}", path);
            e.printStackTrace();

            return false;
        }
    }



    public static String getFileContent(File f) throws FileNotFoundException {

        Scanner sc = new Scanner(f);
        StringBuilder content =new StringBuilder("");
        while (sc.hasNextLine()){
            content.append(sc.nextLine()).append("\n");

        }
        sc.close();
        return content.toString();
    }
    public static String generateFromTemplate(String template, HashMap<String, String> binder)
    {
        try {
            return new SimpleTemplateEngine().createTemplate(template).make(binder).toString();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

    public static String packageToPath(String packageStr)
    {
        return packageStr.replace(".", "/");
    }
    public static String pathToPackage(String path)
    {
        return path.replace("/", ".");
    }
    public static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }
}
