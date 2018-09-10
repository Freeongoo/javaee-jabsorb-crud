import Types from "../ValidatorTypes";

export default {
    firstName: {
        rules: [Types.isNotEmpty], // string because can set many rules, example 'isNotEmpty|isCorrectRetypePassword'
        label: 'First Name'
    },

    lastName: {
        rules: [Types.isNotEmpty],
        label: 'Last Name'
    },

    userName: {
        rules: [Types.isNotEmpty],
        label: 'User Name'
    },

    password: {
        rules: [Types.isNotEmpty],
        label: 'Password'
    },

    password2: {
        rules: [Types.isNotEmpty, Types.isCorrectRetypePassword],
        label: 'Retype password'
    },
};