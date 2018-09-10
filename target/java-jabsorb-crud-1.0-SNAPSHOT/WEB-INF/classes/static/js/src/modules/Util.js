export default {

    isEmailValid(email) {
        return /^[-!#$%&\'*+\\.\/0-9=?a-zA-Z^_`{|}~]+@([a-zA-Z0-9\._-]+\.)+([0-9a-zA-Z]){2,}$/.test(email);
    },

    convertValueToBoolean(value) {
        if (value == undefined) return false;
        if (value == null) return false;
        if (typeof value === 'string')
            value = value.replace(/\s/g, '');       // trim spaces
        return !!value;
    }
}