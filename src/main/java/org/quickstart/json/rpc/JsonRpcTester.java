package org.quickstart.json.rpc;

/**
 * For test json Rpc
 * curl -X POST "http://localhost:8081/context/JSON-RPC" -H  "accept: application/json" -H  "Content-Type: application/json" -H  "X-CSRF-TOKEN: " -d "{  \"method\": \"jsonRpcTester.getRandomNumber\",  \"params\": []}"
 */
public class JsonRpcTester {
	public double getRandomNumber() {
		return (Math.random());
	}
}
