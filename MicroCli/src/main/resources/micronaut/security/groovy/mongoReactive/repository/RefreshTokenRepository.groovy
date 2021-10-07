package ${securityPackage}.repository

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import ${securityPackage}.domains.RefreshToken
import org.bson.BsonDocument
import org.bson.BsonString
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
class RefreshTokenRepository
{
    private static final Logger log = LoggerFactory.getLogger(RefreshTokenRepository.class)

    private final MongoClient mongoClient
    private MongoCollection<RefreshToken> collection
    RefreshTokenRepository(MongoClient mongoClient){

        this.mongoClient = mongoClient
        log.info("Creating Collection for RefreshTokenRepository")
        collection =  mongoClient.getDatabase("database")
                .getCollection("refreshToken", RefreshToken.class)
    }

    private MongoCollection<RefreshToken> getCollection()
    {
        return collection
    }

    private Mono<RefreshToken> findAsMono(BsonDocument query)
    {
        log.info("Finding query : {}", query)
        return Mono.from(getCollection()
                .find(query))
    }

    private Flux<RefreshToken> findAsFlux(BsonDocument query)
    {
        log.info("Finding query : {}", query)
        return Flux
                .from(getCollection()
                        .find(query))
    }


    Mono<RefreshToken> save(RefreshToken object)
    {

        log.info("Saving RefreshToken : {}", object)
        if(object.getId() == null)
        {
            return Mono.just(object)
                    .onErrorReturn(object)
        }
        return Mono.from(getCollection().insertOne(object)
        ).map(success ->object)
    }

    Flux<RefreshToken> findAll()
    {
        log.info("Finding All RefreshToken ")
        return Flux.from(getCollection().find())
    }

    Mono<RefreshToken> findById(final String id){
        log.info("Finding RefreshToken by Id : {}", id)
        final BsonDocument document = new BsonDocument()
        document.append("_id", new BsonString(id))
        return findAsMono(document)
    }

    Mono<Boolean> deleteById(String id) {
        log.info("Deleting RefreshToken by id: {}", id)
        BsonDocument document = new BsonDocument()
        document.append("_id", new BsonString(id))
        return Mono.from(getCollection().deleteOne(document))
                .map(success->Boolean.TRUE)
                .onErrorReturn(Boolean.FALSE)
    }
    Mono<RefreshToken> update(RefreshToken object)  {
        log.info("Deleting RefreshToken of Username: {}", object.getUsername())

        BsonDocument document = new BsonDocument()
        document.append("_id", new BsonString(object.getId()))
        return  Mono.from(getCollection().findOneAndReplace(document,object ))
    }

    Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        log.info("Find by refresh token: {}", refreshToken)
        BsonDocument document = new BsonDocument()
        document.append("refreshToken", new BsonString(refreshToken))
        return  findAsMono(document).blockOptional()
    }
}
