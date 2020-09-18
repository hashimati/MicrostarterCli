package io.hashimati.microcli.utils;

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
        put("string", "varchar2 ");
        put("date", "timestamp");
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
    }};

    public final static HashMap<String ,String> postgresMapper = new HashMap<String, String>(){{

        putIfAbsent("int", "integer");
        put("byte", "smallint");
        put("long", "bigint");
        put("short", "mediumint");
        put("boolean", "bit");
        put("char", "char");
        put("float", "float");
        put("double", "double");
        put("string", "varchar");
        put("date", "timestamp");
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
    }};
}
