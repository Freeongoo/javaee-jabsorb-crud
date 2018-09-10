import config from './config';
import Validator from "../Validator";

export default class CreateValidator extends Validator{
    constructor() {
        super(config);
    }
}