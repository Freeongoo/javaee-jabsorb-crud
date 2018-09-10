import JsonRpcWrapper from "../modules/JsonRpcWrapper";
import EditValidator from "../modules/validate/edit/EditValidator";

export default class EditUserModel {
    updateUser(callback, data) {
        let jsonRpc = new JsonRpcWrapper();
        jsonRpc.request('jsonRpcUser', 'update', callback, data)
    }

    getUserInfoById(callback, data) {
        let jsonRpc = new JsonRpcWrapper();
        jsonRpc.request('jsonRpcUser', 'getUserInfoById', callback, data)
    }

    validateForm(data) {
        let validator = new EditValidator();
        validator.validate(data);

        if (validator.hasErrors()) return validator.getErrors();
        return null;
    }
}