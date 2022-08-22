package io.hashimati.microcli.utils;
/**
 * @author Ahmed Al Hashmi
 */
import java.util.HashMap;


public class DataTypeMapper
{

    public final static HashMap<String ,String> wrapperMapper = new HashMap<String, String>(){{

        putIfAbsent("int", "Integer");
        put("byte", "Byte");
        put("long", "Long");
        put("short", "Short");
        put("boolean", "Boolean");
        put("char", "Character");
        put("float", "Float");
        put("double", "Double");
        put("string", "String");
        put("date", "Date");
        put("String", "String");
        put("file", "String");
        put("BigInteger", "BigInteger");
        put("BigDecimal", "BigDecimal");
    }};

    public final static HashMap<String ,String> mysqlMapper = new HashMap<String, String>(){{

        putIfAbsent("int", "int");
        put("byte", "smallint");
        put("long", "bigint");
        put("short", "mediumint");
        put("boolean", "bit");
        put("char", "char");
        put("float", "float");
        put("double", "double");
        put("string", "varchar");
        put("date", "timestamp");
        put("file", "varchar");
        put("BigInteger", "bigint");
        put("BigDecimal", "double");
    }};

    public final static HashMap<String ,String> oracleMapper = new HashMap<String, String>(){{

        putIfAbsent("int", "integer");
        put("byte", "number(5)");
        put("long", "number(38,0");
        put("short", "mediumint");
        put("boolean", "number(1)");
        put("char", "char");
        put("float", "float");
        put("double", "float(24)");
        put("string", "varchar2");
        put("date", "timestamp");
        put("file", "varchar2");
        put("BigInteger", "number(38,0)");
        put("BigDecimal", "float(24)");
    }};

    public final static HashMap<String ,String> mssqlMapper = new HashMap<String, String>(){{

        putIfAbsent("int", "int");
        put("byte", "smallint");
        put("long", "bigint");
        put("short", "int");
        put("boolean", "bit");
        put("char", "char");
        put("float", "float");
        put("double", "float");
        put("string", "varchar");
        put("date", "datetime");
        put("file", "varchar");
        put("BigInteger", "bigint");
        put("BigDecimal", "float");
    }};

    public final static HashMap<String ,String> postgresMapper = new HashMap<String, String>(){{

        putIfAbsent("int", "integer");
        put("byte", "smallint");
        put("long", "bigint");
        put("short", "mediumint");
        put("boolean", "boolean");
        put("char", "char");
        put("float", "float");
        put("double", "double");
        put("string", "varchar");
        put("date", "timestamp");
        put("file", "varchar");
        put("BigInteger", "bigint");
        put("BigDecimal", "double");
    }};

    public final static HashMap<String ,String> liquibaseMapper = new HashMap<String, String>(){{

        putIfAbsent("int", "int");
        put("byte", "smallint");
        put("long", "bigint");
        put("short", "mediumint");
        put("boolean", "boolean");
        put("char", "char");
        put("float", "float");
        put("double", "float");
        put("string", "varchar");
        put("date", "datetime");
        put("file", "varchar");
        put("BigDecimal", "NUMERIC");
        put("BigInteger", "bigint");

    }};


    public final static HashMap<String, String> dialectMapper = new HashMap<String, String>(){{

        put("mysql", "MYSQL");
        put("oracle", "ORACLE");
        put("mariadb", "MYSQL");
        put("sql server", "SQL_SERVER");
        put("postgres", "POSTGRES");
        put("h2", "H2");
    }};
    public final static HashMap<String ,String> graphqlMapper = new HashMap<String, String>(){{

        putIfAbsent("int", "Int");
        put("byte", "Int");
        put("long", "Int");
        put("short", "Int");
        put("boolean", "Boolean");
        put("char", "String");
        put("float", "Float");
        put("double", "Float");
        put("string", "String");
        put("date", "String");
        put("file", "String");
        put("BigInteger", "Int");
        put("BigDecimal", "Float");
    }};


    public final static HashMap<String ,String> bsonMapper = new HashMap<String, String>(){{

        putIfAbsent("int", "BsonInt64");
        put("byte", "BsonInt64");
        put("long", "BsonInt64");
        put("short", "BsonInt64");
        put("boolean", "BsonBoolean");
        put("char", "BsonString");
        put("float", "BsonDouble");
        put("double", "BsonDouble");
        put("string", "BsonString");
        put("date", "BsonTimestamp");
        put("file", "BsonString");
        put("BigInteger", "BsonString");
        put("BigDecimal", "BsonString");
    }};
}
