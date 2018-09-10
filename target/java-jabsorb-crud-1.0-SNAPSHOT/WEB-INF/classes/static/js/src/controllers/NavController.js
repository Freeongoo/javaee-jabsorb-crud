import CreateNewUserView from "../view/CreateNewUserView";
import CreateNewUserController from "./CreateNewUserController";
import CreateUserModel from "../models/CreateUserModel";

export default class NavController {
    constructor(data) {
        this.model = data.model;
        this.view = data.view;
        this.onCreateNewUser = data.onCreateNewUser;

        this.createNewUserController = new CreateNewUserController({
            model: new CreateUserModel(),
            view:new CreateNewUserView(),
            onCreateNewUser: this.onCreateNewUser,
            onClosePopup: this.handleClosePopup.bind(this)
        });

        this._handleEvents();
    }

    handleClosePopup() {
        this.setActiveItemMenuUserList();
    }

    setActiveItemMenuUserList() {
        this.view.setActiveItemMenuUserList();
    }

    _handleEvents() {
        this.view.onClickUserListEvent.attach(() => {
            this.setActiveItemMenuUserList();
        });
        this.view.onClickCreateNewUserEvent.attach(() => {
            this.view.setActiveItemMenuCreateNewUser();
            this.createNewUserController.showCreateForm();
        });
        this.view.onClickLogoutEvent.attach(() => {
            this.model.logout();
        });
    }

    showNav(doAfterShowNavCallback) {
        this.model.getAuthInfo((returnData, exception) => {
            if (exception) {
                alert(exception.msg);
            } else {
                // add context
                returnData.context = CONTEXT;
                this.view.render(returnData);
                doAfterShowNavCallback();
            }
        });
    }
}