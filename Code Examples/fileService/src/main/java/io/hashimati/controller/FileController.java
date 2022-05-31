package io.hashimati.controller;


import io.hashimati.services.FileService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.multipart.CompletedFileUpload;
import jakarta.inject.Inject;

import java.io.File;
import java.io.IOException;

@Controller("/api")
public class FileController {

    @Inject
    private FileService fileServices;

    @Post(value = "/upload/{id}/path", consumes = MediaType.MULTIPART_FORM_DATA)
    public HttpResponse<String> upload(@PathVariable("id") String id, CompletedFileUpload file) throws IOException {
        System.out.println("I'm herer");
        return HttpResponse.ok(fileServices.save(file));
    }
    @Get("/get/{name}")
    public File get(@PathVariable("name") String name)
    {
        return fileServices.readFile(name);
    }
}
