import Util from "../../src/modules/Util";
global.$ = require('jquery');

afterEach(() => {
    document.body.innerHTML = '';
});


test('convertValueToBoolean - undefined', () => {
    expect(Util.convertValueToBoolean(undefined)).toBe(false);
});
test('convertValueToBoolean - null', () => {
    expect(Util.convertValueToBoolean(null)).toBe(false);
});
test('convertValueToBoolean - string', () => {
    expect(Util.convertValueToBoolean("str")).toBe(true);
});
test('convertValueToBoolean - empty string', () => {
    expect(Util.convertValueToBoolean("")).toBe(false);
});
test('convertValueToBoolean - empty spaces string', () => {
    expect(Util.convertValueToBoolean(" ")).toBe(false);
});
test('convertValueToBoolean - number zero', () => {
    expect(Util.convertValueToBoolean(0)).toBe(false);
});
test('convertValueToBoolean - number', () => {
    expect(Util.convertValueToBoolean(2)).toBe(true);
});


test('test email util negative', () => {
    expect(Util.isEmailValid("342")).toBe(false);
});
test('test email util negative', () => {
    expect(Util.isEmailValid("mail.")).toBe(false);
});
test('test email util negative', () => {
    expect(Util.isEmailValid("mail@")).toBe(false);
});
test('test email util negative', () => {
    expect(Util.isEmailValid("mail@12")).toBe(false);
});
test('test email util negative', () => {
    expect(Util.isEmailValid("@12.ru")).toBe(false);
});
test('test email util positive', () => {
    expect(Util.isEmailValid("er@er.ru")).toBe(true);
});


// example test with DOM
/*test('getContext', () => {
    document.body.innerHTML = '<div id="app" data-context="3"></div>';
    expect(Util.getContext()).toBe(3);
});

test('getContext 2', () => {
    expect(Util.getContext()).not.toBeDefined();
});*/