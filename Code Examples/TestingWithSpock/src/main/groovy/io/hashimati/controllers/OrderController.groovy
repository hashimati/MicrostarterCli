package io.hashimati.controllers

import io.micronaut.context.annotation.Parameter
import io.micronaut.http.annotation.*
import javax.inject.Singleton
import io.hashimati.domains.Order
import io.hashimati.services.OrderService
import javax.inject.Inject
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
@Controller("/api/order")
class OrderController {

    @Inject OrderService orderService;
    static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Post("/save")
    @Operation(summary = "Creating a order and Storing in the database",
            description = "A REST service, which saves Order objects to the database."
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Object Supplied")
    @ApiResponse(responseCode = "404", description = "Order not stored")
    Order save(@Body Order order){
        log.info("Saving  Order : {}", order);
        //TODO insert your logic here!
        //saving Object
        return orderService.save(order)
    }


    @Get("/get")
    @Operation(summary = "Getting a order by Id",
        description = "A REST service, which retrieves a Order object by Id."
    )
    @ApiResponse(
        content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "Order not found")
    Order findById(@Parameter("id") long id){

        log.info("Finding Order By Id: {}", id);
        return orderService.findById(id)
    }

    @Delete("/delete/{id}")
    @Operation(summary = "Deleting a order by ID",
            description = "A REST service, which deletes Order object from the database."
    )
    @ApiResponse(
            content = @Content(mediaType = "boolean")
    )
    @ApiResponse(responseCode = "400", description = "Invalid Id Supplied")
    @ApiResponse(responseCode = "404", description = "Order not found")
    Boolean deleteById(@PathVariable("id") long id){
        log.info("Deleting Order by Id: {}", id);
        return  orderService.deleteById(id);
    }


    @Get("/findAll")
    @Operation(summary = "Retrieving all order objects as Json",
            description = "A REST service, which returns all Order objects from the database."
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    Iterable<Order> findAll(){
        log.info("find All")
        return orderService.findAll()
    }

    @Put("/update")
    @Operation(summary = "Updating a order.",
            description = "A REST service, which update a Order objects to the database."
    )
    @ApiResponse(
            content = @Content(mediaType = "application/json")
    )
    @ApiResponse(responseCode = "404", description = "Order not found")
    Order update(@Body Order order)
    {
        log.info("update {}", order)
        return orderService.update(order)
    }
}


