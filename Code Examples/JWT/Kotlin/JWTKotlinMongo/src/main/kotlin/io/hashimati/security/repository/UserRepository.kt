package io.hashimati.security.repository

import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.hashimati.security.domains.User
import jakarta.inject.Singleton
import org.bson.BsonDocument
import org.bson.BsonString
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Singleton
class UserRepository(private val mongoClient: MongoClient) {
    private val collection: MongoCollection<User>

    private fun findAsMono(query: BsonDocument): Mono<User> {
        log.info("Finding query : {}", query)
        return Mono.from(
            collection
                .find(query)
        )
    }

    private fun findAsFlux(query: BsonDocument): Flux<User> {
        log.info("Finding query : {}", query)
        return Flux
            .from(
                collection
                    .find(query)
            )
    }

    fun save(`object`: User): Mono<User> {
        log.info("Saving User : {}", `object`)
        return if (`object`.id == null) {
            Mono.just(`object`)
                .onErrorReturn(`object`)
        } else Mono.from(
            collection.insertOne(`object`)
        ).map { success: InsertOneResult? -> `object` }
    }

    fun findAll(): Flux<User> {
        log.info("Finding All Users ")
        return Flux.from(collection.find())
    }

    fun findById(id: String?): Mono<User> {
        log.info("Finding Fruit by Id : {}", id)
        val document = BsonDocument()
        document.append("_id", BsonString(id))
        return findAsMono(document)
    }

    fun deleteById(id: String?): Mono<Boolean> {
        log.info("Deleting User by id: {}", id)
        val document = BsonDocument()
        document.append("_id", BsonString(id))
        return Mono.from(collection.deleteOne(document))
            .map { success: DeleteResult? -> java.lang.Boolean.TRUE }
            .onErrorReturn(java.lang.Boolean.FALSE)
    }

    fun update(`object`: User): Mono<User> {
        log.info("update user {}", `object`.username)
        val document = BsonDocument()
        document.append("_id", BsonString(`object`.id))
        return Mono.from(collection.findOneAndReplace(document, `object`))
    }

    fun findByUsername(username: String?): Mono<User> {
        log.info("Find user by username {}", username)
        val document = BsonDocument()
        document.append("username", BsonString(username))
        return findAsMono(document)
    }

    fun existsByUsername(username: String?): Boolean {
        log.info("Exist {}", username)
        return findByUsername(username).blockOptional().isPresent
    }

    companion object {
        private val log = LoggerFactory.getLogger(UserRepository::class.java)
    }

    init {
        log.info("Creating Collection for UserRepository")
        collection = mongoClient.getDatabase("database")
            .getCollection("users", User::class.java)
    }
}