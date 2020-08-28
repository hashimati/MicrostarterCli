package io.hashimati.domains




import javax.validation.constraints.*
import groovy.transform.Canonical
import groovy.transform.CompileStatic

import io.micronaut.data.annotation.*

import io.swagger.v3.oas.annotations.media.Schema


import java.util.*

@CompileStatic
@Canonical
@Schema(name="Order", description="Order Description")

@MappedEntity("orders")
class Order{
    
    
    
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    long id
    
    String qoute
	String side
	String dealer
    @DateCreated
    Date dateCreated

    @DateUpdated
    Date dateUpdated
    
    

    
    
}

