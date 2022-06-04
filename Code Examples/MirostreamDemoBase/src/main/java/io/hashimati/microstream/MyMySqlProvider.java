package io.hashimati.microstream;

import one.microstream.afs.sql.types.SqlDataSourceProvider;
import one.microstream.configuration.types.Configuration;

import javax.sql.DataSource;

public class MyMySqlProvider implements SqlDataSourceProvider {
    @Override
    public DataSource provideDataSource(Configuration configuration) {
        return null;
    }
}
