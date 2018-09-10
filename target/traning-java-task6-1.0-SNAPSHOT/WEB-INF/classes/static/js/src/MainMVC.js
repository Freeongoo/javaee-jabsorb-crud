import NavView from "./view/NavView";
import NavController from "./controllers/NavController";
import NavModel from "./models/NavModel";
import UserListController from "./controllers/UserListController";
import UserListModel from "./models/UserListModel";
import UserListView from "./view/UserListView";

export default class MainMVC {
    constructor() {
        this.navElement = document.getElementById('nav-content');
        this.mainContentElement = document.getElementById('main-content');
    }

    initialize() {
        this._initControllers();

        this._defaultAction();
    }

    showUserList() {
        this.userListController.showUserList();
    }

    setActiveItemMenuUserList() {
        this.navController.setActiveItemMenuUserList();
    }

    handleClosePopup() {
        this.setActiveItemMenuUserList();
    }

    handleCreateNewUser() {
        this.showUserList();
        this.setActiveItemMenuUserList();
    }

    _initControllers() {
        this.navController = new NavController({
            model: new NavModel(),
            view: new NavView(this.navElement),
            onCreateNewUser: this.handleCreateNewUser.bind(this)
        });
        this.userListController = new UserListController({
            model: new UserListModel(),
            view: new UserListView(this.mainContentElement),
            onClosePopup: this.handleClosePopup.bind(this)
        });
    }

    _defaultAction() {
        this.navController.showNav(() => {
            this.showUserList();
            this.setActiveItemMenuUserList();
        });
    }
}