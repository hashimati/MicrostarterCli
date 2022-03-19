package io.hashimati.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;




import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micrometer.core.annotation.Timed;
import io.micronaut.tracing.annotation.NewSpan;
import io.micronaut.tracing.annotation.SpanTag;
import jakarta.inject.Inject;

import io.hashimati.domains.Fruit;
import io.hashimati.services.FruitService;

import javax.ws.rs.*;


@Tag(name = "Fruit")
@Path("/api/v1/fruit")
public class FruitController {

    private static final Logger log = LoggerFactory.getLogger(FruitController.class);

    @Inject private FruitService fruitService;


    @NewSpan("Fruit-service")
    @Timed(value = "io.hashimati.controllers.fruitController.save", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for saving fruit object")
    @POST
    @Path("/save")
    @Version("1")
    @Operation(summary = "Creating a fruit and Storing in the database",
            description = "A REST service, which saves Fruit objects to the database.",
            operationId = "SaveFruit"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Object Supplied")
    @ApiResponse(responseCode = "404", description = "Fruit not stored")
    public Fruit save(@SpanTag("save.fruit") @Body Fruit fruit  ){
        log.info("Saving  Fruit : {}", fruit);
        //TODO insert your logic here!

        //saving Object
        return fruitService.save(fruit );
    }


    @NewSpan("Fruit-service")
    @Timed(value = "io.hashimati.controllers.fruitController.findById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by id")
    @GET
    @Path("/get")
    @Version("1")
    @Operation(summary = "Getting a fruit by Id",
        description = "A REST service, which retrieves a Fruit object by Id.",
        operationId = "FindByIdFruit"
    )
    @ApiResponse(
        content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "Fruit not found")
    public Fruit findById(@SpanTag("findById.id") @Parameter("id") long id ){
        return fruitService.findById(id );
    }

    @NewSpan("Fruit-service")
    @Timed(value = "io.hashimati.controllers.fruitController.deleteById", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for deleting a fruit object by id")
    @DELETE
    @Path("/delete/{id}")
    @Version("1")
    @Operation(summary = "Deleting a fruit by ID",
            description = "A REST service, which deletes Fruit object from the database.",
            operationId = "DeleteByIdFruit"
    )
    @ApiResponse(
            content = @Content(mediaType = "boolean")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "Fruit not found")
    public boolean deleteById(@SpanTag("deleteById.id") @PathVariable("id") long id ){
        log.info("Deleting Fruit by Id: {}", id);
        return  fruitService.deleteById(id );
    }

    @NewSpan("Fruit-service")
    @Timed(value = "io.hashimati.controllers.fruitController.findAll", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding all fruit objects")
    @GET @Path("/findAll")
    @Version("1")
    @Operation(summary = "Retrieving all fruit objects as Json",
            description = "A REST service, which returns all Fruit objects from the database.",
            operationId = "FindAllFruit"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    public Iterable<Fruit> findAll( ){
        log.info("find All");
        return fruitService.findAll( );
    }

    @NewSpan("Fruit-service")
    @Timed(value = "io.hashimati.controllers.fruitController.update", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for update a fruit object")
    @PUT
    @Path("/update")
    @Version("1")
    @Operation(summary = "Updating a fruit.",
            description = "A REST service, which update a Fruit objects to the database.",
            operationId = "UpdateFruit"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "404", description = "Fruit not found")
    public Fruit update(@SpanTag("update.fruit") @Body Fruit fruit )
    {
        log.info("update {}", fruit);
        return fruitService.update(fruit );

    }

    
    @GET
    @Path("/findByName")
    @NewSpan("Fruit-service")
    @Timed(value = "io.hashimati.controllers.fruitController.findByName", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by Name")
    @Operation(summary = "Find an entity by Name",
    description = "A REST service, which retrieves a Fruit object by Name."
    )
    @ApiResponse(
    content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "Fruit not found")
    public Fruit findByName(String query ){
          log.info("Finding Fruit By Name: {}", query);
          return fruitService.findByName(query );
    }


    @NewSpan("Fruit-service")
    @Timed(value = "io.hashimati.controllers.fruitController.findAllByLetter", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for finding a fruit object by Letter")
    
    @GET @Path("/findAllByLetter")
    @Operation(summary = "Getting all entity by Letter",
       description = "A REST service, which retrieves a Fruit objects by Letter."
    )
    @ApiResponse(
       content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "Fruit not found")
    public Iterable<Fruit> findAllByLetter(String query ){
          log.info("Finding all Fruit By Letter: {}", query);
          return fruitService.findAllByLetter(query );
    }





    @NewSpan("Fruit-service")
    @Timed(value = "io.hashimati.controllers.fruitController.updateby.Name", percentiles = { 0.5, 0.95, 0.99 }, description = "Observing all service metric for update a fruit object")
    @PUT @Path("/updateByName")
    @Version("1")
    @Operation(summary = "Updating a fruit.",
            description = "A REST service, which updates a Fruit objects to the database by Name, This service will only update these attributes {name, letter}.",
            operationId = "UpdateFruitbyName"
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "404", description = "Fruit not found")
    public Long updateName(@SpanTag("updateupdateby.Name.fruit") @QueryValue("query") String query,  @Body Fruit body )
    {
        log.info("update {}", query);
        return fruitService.updateByName(query, body.getName(), body.getLetter() );

    }

}


