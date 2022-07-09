package io.hashimati.microstream;


import io.micronaut.context.BeanContext;
import io.micronaut.context.annotation.*;
import io.micronaut.context.exceptions.DisabledBeanException;
import io.micronaut.core.reflect.InstantiationUtils;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.micronaut.microstream.conf.EmbeddedStorageConfigurationProvider;
import io.micronaut.microstream.conf.StorageManagerFactory;
import jakarta.inject.Singleton;
import one.microstream.afs.sql.types.SqlConnector;
import one.microstream.afs.sql.types.SqlFileSystem;
import one.microstream.afs.sql.types.SqlProviderMariaDb;
import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageFoundation;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;
import one.microstream.storage.types.StorageManager;
import org.mariadb.jdbc.MariaDbDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Replaces(factory = StorageManagerFactory.class)
@Factory
public class MicrostreamFactory  {


    private static final Logger LOG = LoggerFactory.getLogger(MicrostreamFactory.class);



    private final BeanContext beanContext;

    /**
     * Constructor.
     *
     * @param beanContext   Bean Context.
     */
    public MicrostreamFactory(BeanContext beanContext) {
        this.beanContext = beanContext;
    }


    @EachBean(EmbeddedStorageFoundation.class)
    @Bean(preDestroy = "shutdown")
    @Singleton
    public StorageManager createStorageManager(EmbeddedStorageFoundation<?> foundation,
                                               @Parameter String name) {


//        MongoDatabase mongoDatabase = mongoClient.getDatabase("microstream");
//        BlobStoreFileSystem fileSystem = BlobStoreFileSystem.New(
//                MongoDbConnector.Caching(database)
//        );

        MariaDbDataSource dataSource = null;
        try {
            dataSource = new MariaDbDataSource();
            dataSource.setUrl("jdbc:mysql://localhost:3306/microstream");
            dataSource.setUser("root");
            dataSource.setPassword("Hello@1234");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        SqlFileSystem fileSystem = SqlFileSystem.New(
                SqlConnector.Caching(
                        SqlProviderMariaDb.New(dataSource)
                )
        );
        EmbeddedStorageManager storageManager = EmbeddedStorage.start(fileSystem.ensureDirectoryPath("microstreamDd")); //foundation.createEmbeddedStorageManager().start();

        System.out.println(storageManager.root() == null);
        if (storageManager.root() == null) {
            if (LOG.isTraceEnabled()) {
                LOG.trace("No data found");
            }


            if (!beanContext.containsBean(EmbeddedStorageConfigurationProvider.class, Qualifiers.byName(name))) {
                throw new DisabledBeanException("Please, define a bean of type " + EmbeddedStorageConfigurationProvider.class.getSimpleName() + " by name qualifier: " + name);
            }
            EmbeddedStorageConfigurationProvider configuration = beanContext.getBean(EmbeddedStorageConfigurationProvider.class, Qualifiers.byName(name));
            if (configuration.getRootClass() != null) {
                storageManager.setRoot(InstantiationUtils.instantiate(configuration.getRootClass()));
            }
            storageManager.storeRoot();
        }
        else{
          storageManager.viewRoots();
        }
        return storageManager;
    }
}
