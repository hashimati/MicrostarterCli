package io.hashimati.repositories


import io.hashimati.domains.Order
import java.util.Optional
import io.micronaut.data.annotation.*
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.model.*
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = Dialect.H2)
interface OrderRepository extends CrudRepository<Order, Long> {
    
}

