import Util from '../Util';

export default {

    isCorrectRetypePassword: {
        validate: function (value, data) {
            let valueAdd = data.password;
            return value == valueAdd
        },
        instructions: function () {
            return "is must be same like field: \"Password\"";
        }
    },


    isNotEmpty: {
        validate: function (value, data) {
            return value !== "";
        },
        instructions: function () {
            return "is required.";
        }
    },

    isNumber: {
        validate: function (value, data) {
            return !isNaN(value);
        },
        instructions: function () {
            return "is only be a number.";
        }
    },

    isEmail: {
        validate: function (value, data) {
            return Util.isEmailValid(value);
        },
        instructions: function () {
            return "must be a valid email address.";
        }
    },

    isAlphaNum: {
        validate: function (value, data) {
            return !/[^a-z0-9]/i.test(value);
        },
        instructions: function () {
            return "the value can only contain characters and numbers, no special symbols";
        }
    }
};