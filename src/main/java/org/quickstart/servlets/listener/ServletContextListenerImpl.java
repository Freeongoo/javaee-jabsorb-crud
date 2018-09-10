package org.quickstart.servlets.listener;

import org.jabsorb.JSONRPCBridge;
import org.quickstart.components.auth.impl.AuthorizationImpl;
import org.quickstart.components.validate.impl.ValidateParamsImpl;
import org.quickstart.json.rpc.JsonRpcTester;
import org.quickstart.json.rpc.JsonRpcUser;
import org.quickstart.repository.mysql.UserRepositoryMySql;
import org.quickstart.repository.mysql.UserRoleRepositoryMySql;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
        jsonRpcBridgeInitialize();
	}

	private void jsonRpcBridgeInitialize() {
        JSONRPCBridge jsonrpcBridge = JSONRPCBridge.getGlobalBridge();

        /*
		curl -X POST "http://localhost:8081/context/JSON-RPC" -H  "accept: application/json" -H  "Content-Type: application/json" -H  "X-CSRF-TOKEN: " -d "{  \"method\": \"jsonRpcTester.getRandomNumber\",  \"params\": []}"
		*/
        JsonRpcTester jsonRpcTester = new JsonRpcTester();
        jsonrpcBridge.registerObject("jsonRpcTester", jsonRpcTester);

        JsonRpcUser jsonRpcUser = new JsonRpcUser(
                new UserRepositoryMySql(),
                new UserRoleRepositoryMySql(),
                new AuthorizationImpl(new UserRoleRepositoryMySql()),
				new ValidateParamsImpl()
        );
        jsonrpcBridge.registerObject("jsonRpcUser", jsonRpcUser);
    }

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
