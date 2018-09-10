package org.quickstart.repository;

import org.junit.After;
import org.junit.Before;
import org.quickstart.db.ConnectionHolder;
import org.quickstart.db.DatabaseConfig;
import org.quickstart.db.connection.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractRepository {
	private Connection conn;

	@Before
	public void setUp() throws SQLException {
		DatabaseConfig.setPropFileName("db-test.properties");
		conn = new DataSource().getConnection();
		conn.setAutoCommit(false);
		ConnectionHolder.set(conn);
	}

	@After
	public void tearDown() throws SQLException {
		ConnectionHolder.set(null);
		conn.rollback();
		conn.setAutoCommit(true);
		conn.close();
		DatabaseConfig.setPropFileName(null);
	}
}
