package io.hashimati.microcli.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.constants.ProjectConstants;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;
import static javax.xml.xpath.XPathConstants.NODE;

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



    public static boolean dumpContentToFile(String path, String content){

        if(content.isEmpty()) return false;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(path));
            pw.print(content);
            pw.flush();
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return  false;
        }
    }
    public static boolean appendContentToFile(String path, String content){

        if(content.isEmpty()) return false;
        File file = new File(path);
        if(!file.exists()) {
            try {
                System.out.println("i' here  " + path + content);
                return createFile(path, content);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(file);
            pw.append("\n");
            pw.append(content);
            pw.flush();
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return  false;
        }

    }
    public static boolean appendContentToFileFromIndex(String path, String content, int startIndex){

        if(content.isEmpty()) return false;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new File(path));
            pw.write("\n"+content+"\n",startIndex, ("\n"+content+"\n").length() );
            pw.flush();
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return  false;
        }
    }

    /**
     *
     * @param path
     * @param content
     * @apiNote to be used used for create source files.
     * @return
     * @throws IOException
     */
    public static boolean createFile(String path, String content) throws IOException {

        File file = new File( path);
        File parent = file.getParentFile();
       // parent.setWritable(true);
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


    public static boolean deleteFile(String path)  {
        File file = new File(path);
        try {
             FileUtils.forceDelete(file);
            System.out.println("Deleted: " + file.getAbsolutePath());
             return true;
        } catch (IOException e) {
            return false;
        }
    }
    public static boolean deleteDirectory(String path)  {
        File directory = new File(path);
        try {
            FileUtils.deleteDirectory(directory);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static String srcFileExtension(String lang)
    {
        switch (lang)
        {
            case "java":
                return ".java";
            case "kotlin":
                return ".kt";
            case "groovy":
                return ".groovy";
            default:
                return ".java";
        }
    }

    public static String getFileContent(File f) throws FileNotFoundException {

        if(!f.exists()) return "";
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
        JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }




    @Deprecated
    public static String appendXML(String parentXmlStr, String childXmlStr, String parentPath, String childPath) throws XPathExpressionException, TransformerException {
        InputSource parent  =  new InputSource(parentXmlStr);

        System.out.println(parent.getPublicId());
        InputSource child = new InputSource(childXmlStr);

        XPath xPath = XPathFactory.newInstance().newXPath();
        Node parentNode  = (Node)xPath.evaluate(parentPath, parent, NODE);
        System.out.println(parentNode.getBaseURI());
        Document parentDoc = parentNode.getOwnerDocument();
        System.out.println(parentDoc);
        Node childNode = (Node)xPath.evaluate(childPath, child, NODE);

        parentNode.getParentNode().replaceChild(
                parentDoc.adoptNode(childNode), parentNode
        );
        TransformerFactory.newInstance()
                .newTransformer()
                .transform(new DOMSource(parentDoc), new StreamResult(System.out));
        return "done";
    }



    @Deprecated
    public static Document read(String path) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(path);
    }


    public static String getValueBetweenTag(String tag, String str)
    {
        Pattern pattern = Pattern.compile("<"+tag+">(.*?)</"+tag+">", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(str);
        String result = "";
        while (matcher.find()) {
            result+= matcher.group(1);
        }
        return result;
    }


    public static String listToString(String separator, String... list)
    {

        return Arrays.stream(list)
                .reduce((x,y)->x+separator+y)
                .get();
    }

    public static String getSourceFileExtension(String lang) {
        String extension =".";
        switch (lang.toLowerCase())
        {
            case KOTLIN_LANG:
                return new StringBuilder().append(extension).append(ProjectConstants.Extensions.KOTLIN).toString();
            case GROOVY_LANG:
                return new StringBuilder().append(extension).append(ProjectConstants.Extensions.GROOVY).toString();
            case JAVA_LANG:
            default:
                return new StringBuilder().append(extension).append(ProjectConstants.Extensions.JAVA).toString();


        }
    }
}
