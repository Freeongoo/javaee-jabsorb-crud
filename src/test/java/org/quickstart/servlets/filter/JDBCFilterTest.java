package org.quickstart.servlets.filter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.quickstart.db.connection.DataSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

public class JDBCFilterTest {
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private FilterChain chain;
    @Mock private DataSource ds;
    @Mock private Connection conn;
    @InjectMocks private JDBCFilter jdbcFilter;

    @Before
    public void setUp() throws SQLException {
        jdbcFilter = new JDBCFilter();
        MockitoAnnotations.initMocks(this);

		when(ds.getConnection()).thenReturn(conn);
    }

    @Test()
    public void doFilter_WhenAllIsGood() throws SQLException, IOException, ServletException {
        jdbcFilter.doFilter(request, response, chain);

        verify(conn).setAutoCommit(false);
        verify(conn, never()).rollback();
        verify(conn).commit();
    }

    @Test(expected = ServletException.class)
    public void doFilter_WhenExceptionThrowInChain() throws SQLException, IOException, ServletException {
        doThrow(new ServletException())
        .when(chain)
        .doFilter(request, response);

        jdbcFilter.doFilter(request, response, chain);

		verify(conn).setAutoCommit(false);
        verify(conn).rollback();
        verify(conn, never()).commit();
    }

    @Test(expected = ServletException.class)
    public void doFilter_WhenExceptionThrowInChainAndThrowWhenRollback() throws SQLException, IOException, ServletException {
        doThrow(new SQLException())
        .when(conn)
        .rollback();

        doThrow(new ServletException())
        .when(chain)
        .doFilter(request, response);

        jdbcFilter.doFilter(request, response, chain);

		verify(conn).setAutoCommit(false);
		verify(conn).rollback();
		verify(conn, never()).commit();
    }
}