import Event from "../event/Event";

export default class CreateNewUserView {
    constructor() {
        this.onSubmitForm = new Event(this);
        this._handleEvents();
    }

    _handleEvents() {
        let self = this;

        $('body').on('submit', '#create-form', function(evt) {
            evt.preventDefault();
            let data = $(this).serializeArray().reduce(function(obj, item) {
                obj[item.name] = item.value;
                return obj;
            }, {});
            self.onSubmitForm.notify(data);
            return false;
        });
    }

    _getHtml() {
        return `
            <form id="create-form" method="post" action="#">
              <div class="form-group">
                <label for="userName">Username:</label>
                <input autocomplete="off" type="text" name="userName" class="form-control" id="userName">
              </div>
              <div class="form-group">
                <label for="pwd">Password:</label>
                <input autocomplete="off" name="password" type="password" class="form-control" id="pwd">
              </div>
              <div class="form-group">
                <label for="pwd2">Retype password:</label>
                <input autocomplete="off" name="password2" type="password" class="form-control" id="pwd2">
              </div>
              <div class="form-group">
                <label for="firstName">First Name:</label>
                <input autocomplete="off" type="text" name="firstName" class="form-control" id="firstName">
              </div>
              <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input autocomplete="off" type="text" name="lastName" class="form-control" id="lastName">
              </div>
              <div class="form-check">
                  <input class="form-check-input" type="checkbox" name="manager" id="manager">
                  <label class="form-check-label" for="manager">
                    Manager
                  </label>
              </div>
              <button type="submit" class="btn btn-default">Submit</button>
            </form>
        `;
    }

    clearErrorMsgForm() {
        $(".error-input").remove();
    }

    /**
     * errors = {inputName: "error message"}
     *
     * @param errors
     */
    setErrorForm(errors) {
        let inputName;
        for (inputName in errors) {
            let msg = errors[inputName];
            $('input[name="'+ inputName +'"]').parent().
                append(`<div class='alert alert-danger error-input'>${msg}</div>`)
        }
    }

    render(data) {
        this.data = data;
        return this._getHtml();
    }
}