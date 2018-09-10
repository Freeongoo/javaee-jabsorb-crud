import JsonRpcWrapper from "../modules/JsonRpcWrapper";
import CreateValidator from "../modules/validate/create/CreateValidator";

export default class CreateUser {
    createNewUser(callback, data) {
        let jsonRpc = new JsonRpcWrapper();
        jsonRpc.request('jsonRpcUser', 'create', callback, data)
    }

    validateForm(data) {
        let validator = new CreateValidator();
        validator.validate(data);

        if (validator.hasErrors()) return validator.getErrors();
        return null;
    }
}