import CreateValidator from "../../../../src/modules/validate/create/CreateValidator";

global.$ = require('jquery');

test('test create validator - all fields correct', () => {
    let createValidator = new CreateValidator();

    let data = {};
    data.userName = "name";
    data.firstName = "first";
    data.lastName = "last";
    data.password = "qwerty";
    data.password2 = "qwerty";

    createValidator.validate(data);
    expect(createValidator.hasErrors()).toBe(false);
    expect(createValidator.getErrors()).toEqual({})
});

test('test create validator - all fields empty', () => {
    let createValidator = new CreateValidator();

    let data = {};
    data.userName = "";
    data.firstName = "";
    data.lastName = "";
    data.password = "";
    data.password2 = "";

    let expectedErrors = {
        lastName: "The \"Last Name\" field is required.",
        userName: "The \"User Name\" field is required.",
        firstName: "The \"First Name\" field is required.",
        password: "The \"Password\" field is required.",
        password2: "The \"Retype password\" field is required.",
    };

    createValidator.validate(data);
    expect(createValidator.hasErrors()).toBe(true);
    expect(createValidator.getErrors()).toEqual(expectedErrors)
});

test('test create validator - not all field fulled', () => {
    let createValidator = new CreateValidator();

    let data = {};
    data.userName = "";
    data.firstName = "first";
    data.lastName = "";

    let expectedErrors = {
        lastName: "The \"Last Name\" field is required.",
        userName: "The \"User Name\" field is required.",
    };

    createValidator.validate(data);
    expect(createValidator.hasErrors()).toBe(true);
    expect(createValidator.getErrors()).toEqual(expectedErrors)
});

test('test create validator - retype password not equal password', () => {
    let createValidator = new CreateValidator();

    let data = {};
    data.userName = "name";
    data.firstName = "first";
    data.password = "qwerty";
    data.password2 = "qwerty2";

    let expectedErrors = {
        password2: "The \"Retype password\" field is must be same like field: \"Password\"",
    };

    createValidator.validate(data);
    expect(createValidator.hasErrors()).toBe(true);
    expect(createValidator.getErrors()).toEqual(expectedErrors)
});