import Util from "../modules/Util";
import PopupController from "./PopupController";
import PopupView from "../view/PopupView";

export default class CreateNewUserController {
    constructor(data) {
        this.model = data.model;
        this.view = data.view;
        this.onCreateNewUser = data.onCreateNewUser;
        this.onClosePopup = data.onClosePopup;

        this.popupController = new PopupController(PopupView.getInstance(), this.onClosePopup);

        this._handleEvents();
    }

    _handleEvents() {
        this.view.onSubmitForm.attach((sender, data) => {
            this.view.clearErrorMsgForm();

            // validate
            let errors = this.model.validateForm(data);
            if (errors != null) {
                this.view.setErrorForm(errors);
                return false;
            }

            data.manager = Util.convertValueToBoolean(data.manager);

            // save data
            this.model.createNewUser((returnData, exception) => {
                if (exception) {
                    alert(exception.msg);
                } else {
                    this.popupController.closePopup();
                    this.onCreateNewUser();
                }
            }, data);
        });
    }

    showCreateForm() {
        return this.popupController.show("Create New User", this.view.render());
    }
}