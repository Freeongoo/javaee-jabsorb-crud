// abstract class
export default class Validator {
    constructor(config) {
        this.config = config;
        this.errors = {};
        this.currentField = null;
    }

    validate(data) {
        for (let fieldName in data) {
            if (!data.hasOwnProperty(fieldName)) continue;
            if (this.config[fieldName] === undefined) continue;

            this.currentField = fieldName;

            let listRules = this.config[fieldName].rules;
            this._validateByRules(listRules, data);
        }
    }

    _validateByRules(listRules, data) {
        for (let j = 0; j < listRules.length; j++) {
            if (!this._validateByRule(listRules[j], data)) {
                break;
            }
        }
    }

    _validateByRule(rule, data) {
        let isValid = rule.validate(data[this.currentField], data);
        if (!isValid) this._setErrorMsg(rule);
        return isValid;
    }

    _setErrorMsg(rule) {
        this.errors[this.currentField] = "The \"" + this.config[this.currentField].label +
            "\" field " + rule.instructions();
    }

    getErrors() {
        return this.errors;
    }

    hasErrors() {
        return Object.getOwnPropertyNames(this.errors).length > 0;
    }
};