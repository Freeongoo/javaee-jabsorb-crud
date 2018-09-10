import JsonRpcWrapper from "../modules/JsonRpcWrapper";

export default class UserList {
    get(callback) {
        let jsonRpc = new JsonRpcWrapper();
        jsonRpc.request('jsonRpcUser', 'getListWithAuthInfo', callback)
    }

    deleteUser(callback, data) {
        let jsonRpc = new JsonRpcWrapper();
        jsonRpc.request('jsonRpcUser', 'delete', callback, data)
    }
}