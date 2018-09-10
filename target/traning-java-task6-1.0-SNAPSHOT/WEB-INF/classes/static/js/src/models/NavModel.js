import JsonRpcWrapper from "../modules/JsonRpcWrapper";

export default class Nav {

    getAuthInfo(callback) {
        let jsonRpc = new JsonRpcWrapper();
        jsonRpc.request('jsonRpcUser', 'getAuthUserInfo', callback)
    }

    logout() {
        $.ajax({
            url: CONTEXT + "/logout",
            type: "POST",
            error: function(e, msg) {
                // ignore
            },
            complete: function() {
                window.location.reload();
            }
        });
    }
}