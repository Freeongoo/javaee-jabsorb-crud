import Util from "../modules/Util";
import PopupController from "./PopupController";
import PopupView from "../view/PopupView";

export default class EditUserController {
    constructor(data) {
        this.model = data.model;
        this.view  = data.view;
        this.onClosePopup = data.onClosePopup;
        this.onUpdateUser = data.onUpdateUser;

        this.popupController = new PopupController(PopupView.getInstance(), this.onClosePopup);

        this._handleEvents();
    }

    showEditForm(userId) {
        this._showForm(userId);
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

            // update data
            this.model.updateUser((returnData, exception) => {
                if (exception) {
                    alert(exception.msg);
                } else {
                    this.popupController.closePopup();
                    this.onUpdateUser();
                }
            }, data);
        });
    }

    _showForm(userId) {
        this.model.getUserInfoById((returnData, exception) => {
            if (exception) {
                alert(exception.msg);
            } else {
                return this.popupController.show("Edit User", this.view.render(returnData));
            }
        }, userId);
    }
}