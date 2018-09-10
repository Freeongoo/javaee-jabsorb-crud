package org.quickstart.json.rpc;

import org.jabsorb.callback.ErrorInvocationCallback;
import org.quickstart.db.ConnectionHolder;

import java.lang.reflect.AccessibleObject;
import java.sql.Connection;
import java.sql.SQLException;

public class TransactionErrorInvocationCallback implements ErrorInvocationCallback {
	@Override
	public void invocationError(Object context, Object instance, AccessibleObject accessibleObject, Throwable error) {
		Connection conn = ConnectionHolder.get();
		try {
			conn.rollback();
			conn.close();
			ConnectionHolder.set(null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void preInvoke(Object context, Object instance, AccessibleObject accessibleObject, Object[] arguments) throws Exception {
	}

	@Override
	public void postInvoke(Object context, Object instance, AccessibleObject accessibleObject, Object result) throws Exception {
	}
}
