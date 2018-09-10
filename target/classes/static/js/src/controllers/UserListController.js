import EditUserView from "../view/EditUserView";
import EditUserModel from "../models/EditUserModel";
import EditUserController from "./EditUserController";

export default class UserListController {
    constructor(data) {
        this.model = data.model;
        this.view = data.view;
        this.onClosePopup = data.onClosePopup;

       this.editUserController = new EditUserController({
            model: new EditUserModel(),
            view: new EditUserView(),
            onClosePopup: this.onClosePopup,
            onUpdateUser: this.onUpdateUser.bind(this)
        });

        this._handleEvents();
    }

    _handleEvents() {
        this.view.onDeleteUser.attach((sender, id) => {
            // delete user
            this.model.deleteUser((returnData, exception) => {
                if (exception) {
                    alert(exception.msg);
                } else {
                    this.showUserList();
                }
            }, id);
        });

        this.view.onEditUser.attach((sender, userId) => {
            this.editUserController.showEditForm(userId)
        });
    }

    onUpdateUser() {
        this.showUserList();
    }

    showUserList() {
        this.model.get((data, exception) => {
            if (exception) {
                alert(exception.msg);
            } else {
                this.view.render(data);
            }
        });
    }
}