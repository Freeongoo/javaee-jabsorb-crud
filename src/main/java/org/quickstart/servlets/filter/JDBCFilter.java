package org.quickstart.servlets.filter;

import org.quickstart.db.ConnectionHolder;
import org.quickstart.db.connection.DataSource;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.Connection;

@WebFilter(filterName = "JDBCFilter", urlPatterns = {"/*"})
public class JDBCFilter implements Filter {
	private DataSource ds;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		ds = new DataSource();
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		try (Connection conn = ds.getConnection()) {
			try {

				conn.setAutoCommit(false);

				ConnectionHolder.set(conn);

				filterChain.doFilter(servletRequest, servletResponse);

				conn.commit();

			} catch (Exception e) {
				try {
					conn.rollback();
				} catch (Exception e1) {
					e.addSuppressed(e1);
				}
				throw new ServletException(e.getMessage(), e);
			} finally {
                ConnectionHolder.set(null);
            }
		} catch (Exception e) {
            ConnectionHolder.set(null);
			throw new ServletException(e.getMessage(), e);
		}
	}

	@Override
	public void destroy() {
	}
}
