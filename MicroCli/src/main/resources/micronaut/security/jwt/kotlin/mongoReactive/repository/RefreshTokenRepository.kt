package ${securityPackage}.repository

import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import ${securityPackage}.domains.RefreshToken
import jakarta.inject.Singleton
import org.bson.BsonDocument
import org.bson.BsonString
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*

@Singleton
class RefreshTokenRepository(private val mongoClient: MongoClient) {
    private val collection: MongoCollection<RefreshToken>

    private fun findAsMono(query: BsonDocument): Mono<RefreshToken> {
        log.info("Finding query : {}", query)
        return Mono.from(
            collection
                .find(query)
        )
    }

    private fun findAsFlux(query: BsonDocument): Flux<RefreshToken> {
        log.info("Finding query : {}", query)
        return Flux
            .from(
                collection
                    .find(query)
            )
    }

    fun save(`object`: RefreshToken): Mono<RefreshToken> {
        log.info("Saving RefreshToken : {}", `object`)
        return if (`object`.id == null) {
            Mono.just(`object`)
                .onErrorReturn(`object`)
        } else Mono.from(
            collection.insertOne(`object`)
        ).map { success: InsertOneResult? -> `object` }
    }

    fun findAll(): Flux<RefreshToken> {
        log.info("Finding All RefreshToken ")
        return Flux.from(collection.find())
    }

    fun findById(id: String?): Mono<RefreshToken> {
        log.info("Finding RefreshToken by Id : {}", id)
        val document = BsonDocument()
        document.append("_id", BsonString(id))
        return findAsMono(document)
    }

    fun deleteById(id: String?): Mono<Boolean> {
        log.info("Deleting RefreshToken by id: {}", id)
        val document = BsonDocument()
        document.append("_id", BsonString(id))
        return Mono.from(collection.deleteOne(document))
            .map { success: DeleteResult? -> java.lang.Boolean.TRUE }
            .onErrorReturn(java.lang.Boolean.FALSE)
    }

    fun update(`object`: RefreshToken): Mono<RefreshToken> {
        log.info("Deleting RefreshToken of Username: {}", `object`.username)
        val document = BsonDocument()
        document.append("_id", BsonString(`object`.id))
        return Mono.from(collection.findOneAndReplace(document, `object`))
    }

    fun findByRefreshToken(refreshToken: String?): Optional<RefreshToken> {
        log.info("Find by refresh token: {}", refreshToken)
        val document = BsonDocument()
        document.append("refreshToken", BsonString(refreshToken))
        return findAsMono(document).blockOptional()
    }

    companion object {
        private val log = LoggerFactory.getLogger(RefreshTokenRepository::class.java)
    }

    init {
        log.info("Creating Collection for RefreshTokenRepository")
        collection = mongoClient.getDatabase("database")
            .getCollection("refreshToken", RefreshToken::class.java)
    }
}