const RPC_URL = 'JSON-RPC';

export default class JsonRpcWrapper {
    request(object, method, callback, params) {
        let rpcClient = new JSONRpcClient(RPC_URL);

        // TODO: change this...
        if (params !== undefined)
            rpcClient[object][method](callback, params);
        else
            rpcClient[object][method](callback);
    }

    /**
     * With jQuery ajax
     * TODO: How handle exception?
     *
     * @param method
     * @param params
     * @param completeCallback
     * @param errorCallback
     */
    requestOld(method, params, completeCallback, errorCallback) {
        let objParam = {
            method: method,
            params: params
        };

        let json = JSON.stringify (objParam);
        let url = CONTEXT + "/" + RPC_URL;

        $.ajax({
            url: url,
            data: json,
            type: "POST",
            dataType: "json",
            error: errorCallback,
            complete: completeCallback
        });
    }
}