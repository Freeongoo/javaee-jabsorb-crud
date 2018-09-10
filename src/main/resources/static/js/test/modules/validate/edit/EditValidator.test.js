import EditValidator from "../../../../src/modules/validate/edit/EditValidator";

global.$ = require('jquery');

test('test create validator - all fields correct', () => {
    let editValidator = new EditValidator();

    let data = {};
    data.userName = "name";
    data.firstName = "first";
    data.lastName = "first";
    data.password = "qwerty";
    data.password2 = "qwerty";

    editValidator.validate(data);
    expect(editValidator.hasErrors()).toBe(false);
    expect(editValidator.getErrors()).toEqual({})
});

test('test create validator - all fields correct without update password', () => {
    let editValidator = new EditValidator();

    let data = {};
    data.userName = "name";
    data.firstName = "first";
    data.lastName = "first";
    data.password = "";
    data.password2 = "";

    editValidator.validate(data);
    expect(editValidator.hasErrors()).toBe(false);
    expect(editValidator.getErrors()).toEqual({})
});

test('test create validator - all fields empty', () => {
    let editValidator = new EditValidator();

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
    };

    editValidator.validate(data);
    expect(editValidator.hasErrors()).toBe(true);
    expect(editValidator.getErrors()).toEqual(expectedErrors)
});

test('test create validator - not all field fulled', () => {
    let editValidator = new EditValidator();

    let data = {};
    data.userName = "";
    data.firstName = "first";
    data.lastName = "";

    let expectedErrors = {
        lastName: "The \"Last Name\" field is required.",
        userName: "The \"User Name\" field is required.",
    };

    editValidator.validate(data);
    expect(editValidator.hasErrors()).toBe(true);
    expect(editValidator.getErrors()).toEqual(expectedErrors)
});

test('test create validator - retype password not equal password', () => {
    let editValidator = new EditValidator();

    let data = {};
    data.userName = "name";
    data.firstName = "first";
    data.password = "qwerty";
    data.password2 = "qwerty2";

    let expectedErrors = {
        password2: "The \"Retype password\" field is must be same like field: \"Password\"",
    };

    editValidator.validate(data);
    expect(editValidator.hasErrors()).toBe(true);
    expect(editValidator.getErrors()).toEqual(expectedErrors)
});