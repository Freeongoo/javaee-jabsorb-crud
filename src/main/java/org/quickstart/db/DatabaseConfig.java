package org.quickstart.db;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfig {
	private Properties configFile;
	private static String defaultPropFileName = "db.properties";
	private static String propFileName = null;
	private static DatabaseConfig instance;

	private String getPropFileName() {
		if (propFileName == null) return defaultPropFileName;
		return propFileName;
	}

	public static void setPropFileName(String propFileName) {
		DatabaseConfig.propFileName = propFileName;
	}

	private DatabaseConfig() {
		configFile = new Properties();
		try {
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream(getPropFileName());
			configFile.load(inputStream);
		} catch (IOException e) {
			throw new RuntimeException("Error read db properties file");
		}
	}

	static private DatabaseConfig getInstance() {
		if (instance == null) instance = new DatabaseConfig();
		return instance;
	}

	private String getValue(String key) {
		return configFile.getProperty(key);
	}

	public static String getPort() {
		return getInstance().getValue("PORT");
	}

	public static String getHost() {
		return getInstance().getValue("HOST");
	}

	public static String getDbName() {
		return getInstance().getValue("NAME");
	}

	public static String getUser() {
		return getInstance().getValue("USER");
	}

	public static String getPassword() {
		return getInstance().getValue("PASSWORD");
	}

	public static String getDriverClassName() {
	    return getInstance().getValue("JDBC_DRIVER");
    }

	public static String getConnectionString() {
        return getInstance().getValue("CONNECTION_STR") + getHost() + ":" +
                getPort() + "/" + getDbName() +
                "?" + getCharacterEncoding();
    }

    public static Integer getMinIdle() {
        return Integer.valueOf(getInstance().getValue("MIN_IDLE"));
    }

    public static Integer getMaxIdle() {
        return Integer.valueOf(getInstance().getValue("MAX_IDLE"));
    }

    public static Integer getMaxOpenPreparedStatements() {
        return Integer.valueOf(getInstance().getValue("MAX_OPEN_PREPARED_STATEMENTS"));
    }

    public static String getCharacterEncoding() {
	    return "useUnicode=yes&characterEncoding=UTF-8";
    }
}
