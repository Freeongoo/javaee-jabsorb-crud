package org.quickstart.db.connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.quickstart.db.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private BasicDataSource ds;

    public DataSource() {
        ds = new BasicDataSource();
        ds.setDriverClassName(DatabaseConfig.getDriverClassName());
        ds.setUsername(DatabaseConfig.getUser());
        ds.setPassword(DatabaseConfig.getPassword());
        ds.setUrl(DatabaseConfig.getConnectionString());

        // the settings below are optional -- dbcp can work with defaults
        ds.setMinIdle(DatabaseConfig.getMinIdle());
        ds.setMaxIdle(DatabaseConfig.getMaxIdle());
        ds.setMaxOpenPreparedStatements(DatabaseConfig.getMaxOpenPreparedStatements());
    }

    public Connection getConnection() throws SQLException {
        return this.ds.getConnection();
    }
}
