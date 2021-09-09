package io.hashimati.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

import io.hashimati.domains.User;
import io.hashimati.services.UserService;

@Introspected(classes = {User.class})
@Controller("/api/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject private UserService userService;


    @Post("/save")
    @Operation(summary = "Creating a user and Storing in the database",
            description = "A REST service, which saves User objects to the database.",
            operationId = "SaveUser"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Object Supplied")
    @ApiResponse(responseCode = "404", description = "User not stored")
    public User save(@Body User user){
        log.info("Saving  User : {}", user);
        //TODO insert your logic here!

        //saving Object
        return userService.save(user);
    }
@Get("/dd")
    public User savex(){
        //TODO insert your logic here!

    System.out.println("I'm in Controller");
   User admin =  new User();
    admin.setUsername("Admin");
    admin.setPassword("Hello");
    admin.setEmail("Hello@gmail.com");
    //saving Object
        return userService.save(admin);
    }

    @Get("/get")
    @Operation(summary = "Getting a user by Id",
        description = "A REST service, which retrieves a User object by Id.",
        operationId = "FindByIdUser"
    )
    @ApiResponse(
        content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "User not found")
    public User findById(@Parameter("id") long id){
        return userService.findById(id);
    }

    @Delete("/delete/{id}")
    @Operation(summary = "Deleting a user by ID",
            description = "A REST service, which deletes User object from the database.",
            operationId = "DeleteByIdUser"
    )
    @ApiResponse(
            content = @Content(mediaType = "boolean")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "User not found")
    public boolean deleteById(@PathVariable("id") long id){
        log.info("Deleting User by Id: {}", id);
        return  userService.deleteById(id);
    }

    @Get("/findAll")
    @Operation(summary = "Retrieving all user objects as Json",
            description = "A REST service, which returns all User objects from the database.",
            operationId = "FindAllUser"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    public Iterable<User> findAll(){
        log.info("find All");
        return userService.findAll();
    }

    @Put("/update")
    @Operation(summary = "Updating a user.",
            description = "A REST service, which update a User objects to the database.",
            operationId = "UpdateUser"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "404", description = "User not found")
    public User update(@Body User user)
    {
        log.info("update {}", user);
        return userService.update(user);

    }


}


