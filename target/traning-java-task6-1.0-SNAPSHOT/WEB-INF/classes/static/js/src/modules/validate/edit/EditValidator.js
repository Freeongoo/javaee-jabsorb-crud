import Validator from "../Validator";
import config from "./config";

export default class EditValidator extends Validator {
    constructor() {
        super(config);
    }
}