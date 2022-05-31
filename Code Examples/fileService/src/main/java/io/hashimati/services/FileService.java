package io.hashimati.services;


import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Singleton
public class FileService {

    private static String currentDir = System.getProperty("user.dir");

    private HashMap<String, String> fileRepositroy = new HashMap<>();
    public String save(CompletedFileUpload file) throws IOException {



        fileRepositroy.putIfAbsent(file.getFilename(),currentDir + "/"+ file.getFilename() );
        System.out.println(fileRepositroy);
       return  java.nio.file.Files.copy(
                file.getInputStream(),
                new File(currentDir + "/"+ file.getFilename()).toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING) > 0?"Success":"Failed";
    }

    public File readFile(String name){


        return new File(fileRepositroy.get(name));
    }
}
