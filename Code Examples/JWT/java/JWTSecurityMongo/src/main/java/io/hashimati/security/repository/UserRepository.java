package io.hashimati.security.repository;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.hashimati.security.domains.User;
import jakarta.inject.Singleton;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Singleton
public class UserRepository
{
    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);

    private final MongoClient mongoClient;
    private MongoCollection<User> collection;
    public UserRepository(MongoClient mongoClient){

        this.mongoClient = mongoClient;
        log.info("Creating Collection for UserRepository");
        collection = mongoClient.getDatabase("database")
                .getCollection("users", User.class);
    }

    private MongoCollection<User> getCollection()
    {
        return collection;
    }

    private Mono<User> findAsMono(BsonDocument query)
    {
        log.info("Finding query : {}", query);
        return Mono.from(getCollection()
                .find(query));
    }

    private Flux<User> findAsFlux(BsonDocument query)
    {
        log.info("Finding query : {}", query);
        return Flux
                .from(getCollection()
                        .find(query));
    }


    public Mono<User> save(User object)
    {

        log.info("Saving User : {}", object);
        if(object.getId() == null)
        {
            return Mono.just(object)
                    .onErrorReturn(object);
        }
        return Mono.from(getCollection().insertOne(object)
        ).map(success ->object);
    }

    public Flux<User> findAll()
    {
        log.info("Finding All Users ");
        return Flux.from(getCollection().find());
    }

    public Mono<User> findById(final String id){
        log.info("Finding Fruit by Id : {}", id);
        final BsonDocument document = new BsonDocument();
        document.append("_id", new BsonString(id));
        return findAsMono(document);
    }

    public Mono<Boolean> deleteById(String id) {
        log.info("Deleting User by id: {}", id);
        BsonDocument document = new BsonDocument();
        document.append("_id", new BsonString(id));
        return Mono.from(getCollection().deleteOne(document))
                .map(success->Boolean.TRUE)
                .onErrorReturn(Boolean.FALSE);
    }

    public Mono<User> update(User object)  {
        log.info("update user {}", object.getUsername() );
        BsonDocument document = new BsonDocument();
        document.append("_id", new BsonString(object.getId()));
        return  Mono.from(getCollection().findOneAndReplace(document,object ));

    }

    public Mono<User> findByUsername(String username) {
        log.info("Find user by username {}", username);
        BsonDocument document = new BsonDocument();
        document.append("username", new BsonString(username));
        return  findAsMono(document);

    }

    public boolean existsByUsername(String username) {
        log.info("Exist {}", username);
        return findByUsername(username).blockOptional().isPresent();
    }
}