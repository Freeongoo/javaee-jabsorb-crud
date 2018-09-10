import Event from "../event/Event";

export default class NavView {
    constructor(element) {
        this.titleApp = "User Manager App";
        this.idUserList = 'userList';
        this.idCreateNewUser = 'createNewUser';
        this.idLogout = 'logout';
        this.activeClassItemMenu = 'active';

        this.element = element;
        this.onClickUserListEvent      = new Event(this);
        this.onClickCreateNewUserEvent = new Event(this);
        this.onClickLogoutEvent        = new Event(this);
    }

    _handleEvents() {
        document.getElementById(this.idUserList).addEventListener('click', (e) => {
            e.preventDefault();
            this.onClickUserListEvent.notify();
        });
        document.getElementById(this.idCreateNewUser).addEventListener('click', (e) => {
            e.preventDefault();
            this.onClickCreateNewUserEvent.notify();
        });
        document.getElementById(this.idLogout).addEventListener('click', (e) => {
            e.preventDefault();
            this.onClickLogoutEvent.notify();
        });
    }

    _getHtml() {
        return `
            <nav class="navbar navbar-default navbar-static-top">
                <div class="container">
                    <div class="navbar-header">
        
                        <!-- Collapsed Hamburger -->
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#app-navbar-collapse">
                            <span class="sr-only">Toggle Navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
        
                        <!-- Branding Image -->
                        <a class="navbar-brand" href="${this.data.context}">
                            ${this.titleApp}
                        </a>
                    </div>
        
                    <div class="collapse navbar-collapse" id="app-navbar-collapse">
                        <ul class="nav navbar-nav">
                            &nbsp;
                        </ul>

                        <ul class="nav navbar-nav navbar-right">  
                            <li><a class="main-items" href="#" id="${this.idUserList}">User List</a></li>
                            ${this._isManager() ? `<li><a class="main-items" href="#" id="${this.idCreateNewUser}">Create New User</a></li>` : ''}
                            <li class="main-items dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
                                    ${this.data.user.userName}<span class="caret"></span>
                                </a>
    
                                <ul class="dropdown-menu" role="menu">
                                    <li>
                                        <a href="#" id="${this.idLogout}">Logout</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>
                        </div>
                    </div>
                </nav>
        `;
    }

    _isManager(item) {
        return this.data.manager;
    }

    setActiveItemMenuUserList() {
        this._setNothingActiveItemMenu();
        let element = document.getElementById(this.idUserList);
        element.classList.add(this.activeClassItemMenu);
    }

    setActiveItemMenuCreateNewUser() {
        this._setNothingActiveItemMenu();
        let element = document.getElementById(this.idCreateNewUser);
        element.classList.add(this.activeClassItemMenu);
    }

    _setNothingActiveItemMenu() {
        let items = document.getElementsByClassName("main-items");
        for (let i = 0; i < items.length; i++)
            items[i].className = items[i].className.replace(/\active\b/g, "");
    }

    render(data) {
        this.data = data;
        this.element.innerHTML = this._getHtml();

        this._handleEvents();
    }
}