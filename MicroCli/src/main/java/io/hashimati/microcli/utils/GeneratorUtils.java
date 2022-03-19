package io.hashimati.microcli.utils;
/**
 * @author Ahmed Al Hashmi
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import groovy.text.SimpleTemplateEngine;
import io.hashimati.microcli.constants.ProjectConstants;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import static io.hashimati.microcli.constants.ProjectConstants.LanguagesConstants.*;
import static io.hashimati.microcli.utils.PromptGui.printlnSuccess;
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
            Files.delete(Path.of(path));

             printlnSuccess("Deleted: " + file.getAbsolutePath());
             return true;
        } catch (IOException e) {
            e.printStackTrace();
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
    public static String generateFromTemplateObj(String template, HashMap<String, Object> binder)
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

        //System.out.println(parent.getPublicId());
        InputSource child = new InputSource(childXmlStr);

        XPath xPath = XPathFactory.newInstance().newXPath();
        Node parentNode  = (Node)xPath.evaluate(parentPath, parent, NODE);
       // System.out.println(parentNode.getBaseURI());
        Document parentDoc = parentNode.getOwnerDocument();
        //System.out.println(parentDoc);
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
    public static String appendCodeToScope(String orginalCode, String code){

        return new StringBuilder().append(orginalCode.substring(0, orginalCode.lastIndexOf("}"))).append(code).append("\n}").toString();
    }

    public static  boolean writeBytesToFile(String path, byte[] bytes)
    {
        try {
            Files.write(Path.of(path), bytes);

            return true;
        } catch (IOException e) {
          return false;
        }
    }

    public static boolean unzipFile(String zipFilePath, String destDirectory) throws IOException {
       try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    new File(destDirectory + File.separator + entry.getName()).mkdir();
                    continue;
                }
                File file = new File(destDirectory + File.separator + entry.getName());
                file.getParentFile().mkdirs();
                InputStream in = zipFile.getInputStream(entry);
                OutputStream out = new FileOutputStream(file);
                IOUtils.copy(in, out);
                in.close();
                out.close();

            }
            zipFile.close();
            return true;
        }
        catch(Exception ex)
        {
            return false;
        }

    }


    @Deprecated(forRemoval = true, since = "0.1.1")
    public static boolean unzipFile(String filePath)
    {

//        try {
//
//            System.out.println("I'm herer "+ filePath);
//            ZipFile zipFile = new ZipFile(new File(filePath));
//            System.out.println(zipFile.getName());
//            File unzipFolder = new File(getCurrentWorkingPath() );
//
//            System.out.println(unzipFolder);
//            zipFile.stream().forEach(zipEntry -> {
//                if(zipEntry.isDirectory()){
//                    File destinationFolder = new File(unzipFolder.getPath() + "/" + zipEntry.getName());
//                    try{
//                        destinationFolder.mkdirs();
//                    }
//                    catch(Exception ex)
//                    {
//                        PromptGui.printlnErr("Failed to create "+ destinationFolder.getAbsolutePath());
//                    }
//                }
//                else {
//                    try{
//                        InputStream inputStream = zipFile.getInputStream(zipEntry);
//
//                        boolean creatingFile = new File(getCurrentWorkingPath() + "/" +zipEntry.getName()).createNewFile();
//                        System.out.println(creatingFile);
//                        byte[] data = inputStream.readAllBytes();
//                        writeBytesToFile((getCurrentWorkingPath() + "/" +zipEntry.getName()).replace("\\", "/"), data);
//
//                    }
//                    catch(Exception ex){
//                        ex.printStackTrace();
//                    }
//                }
//            });
//            zipFile.close();
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }

//        // check out https://www.baeldung.com/java-compress-and-uncompress

        try {
            File file = new File(filePath);
            File outFolder = new File(getCurrentWorkingPath());
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
            byte[] buffer = new byte[1024];
            ZipEntry zipEntry = null;
            while((zipEntry = zipInputStream.getNextEntry()) != null) {
                File newFile = newFile(outFolder, zipEntry);
                if (zipEntry.isDirectory()) {
                    if (!newFile.isDirectory() && !newFile.mkdirs()) {
                        throw new IOException("Failed to create directory " + newFile);
                    }
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    if (!parent.isDirectory() && !parent.mkdirs()) {
                        throw new IOException("Failed to create directory " + parent);
                    }

                    // write file content
                    FileOutputStream fos = new FileOutputStream(newFile);
                    int len;
                    while ((len = zipInputStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    fos.close();
                }
            }
            zipInputStream.closeEntry();
            zipInputStream.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Deprecated(forRemoval = true, since = "0.1.1")
    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
    public static String getCurrentWorkingPath(){
        return System.getProperty("user.dir");
    }

}
