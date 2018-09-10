package org.quickstart.servlets.listener;

import org.jabsorb.JSONRPCBridge;
import org.quickstart.json.rpc.TransactionErrorInvocationCallback;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class JsonRpcServletRequestListener implements ServletRequestListener {

	@Override
	public void requestInitialized(ServletRequestEvent servletRequestEvent) {
		ServletRequest servletRequest = servletRequestEvent.getServletRequest();

		JSONRPCBridge jsonrpcBridge = JSONRPCBridge.getGlobalBridge();
		jsonrpcBridge.registerCallback(new TransactionErrorInvocationCallback(), servletRequest.getClass());
	}

	@Override
	public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
		ServletRequest servletRequest = servletRequestEvent.getServletRequest();

		JSONRPCBridge jsonrpcBridge = JSONRPCBridge.getGlobalBridge();
		jsonrpcBridge.unregisterCallback(new TransactionErrorInvocationCallback(), servletRequest.getClass());
	}
}
