import Event from "../event/Event";

export default class UserListView {
    constructor(element) {
        this.element = element;
        this.classEditUser = "edit-user";
        this.classDeleteUser = "delete-user";
        this.textDeleteUser = "Do you really want to delete the user?";

        this.onEditUser   = new Event(this);
        this.onDeleteUser = new Event(this);
    }

    _handleEvents() {
        this._handleDeleteUser();
        this._handleEditUser();
    }

    _handleDeleteUser() {
        let deleteItems = document.getElementsByClassName(this.classDeleteUser);
        for (let i = 0; i < deleteItems.length; i++) {
            deleteItems[i].addEventListener('click', (e) => {
                e.preventDefault();

                // confirm dialog
                let isDelete = confirm(this.textDeleteUser);
                if (!isDelete) return;

                // find user id
                let id = e.target.getAttribute("data-id");
                this.onDeleteUser.notify(id);
            });
        }
    }

    _handleEditUser() {
        let editItems = document.getElementsByClassName(this.classEditUser);
        for (let i = 0; i < editItems.length; i++) {
            editItems[i].addEventListener('click', (e) => {
                e.preventDefault();

                // find user id
                let id = e.target.getAttribute("data-id");
                this.onEditUser.notify(id);
            })
        }
    }

    _getHtml() {
        return `
            <table class="table table-striped custab">
                <thead>
                    <tr>
                        <th>User name</th>
                        ${this._isManager() ? `<th>Actions</th>` : '' }
                    </tr>
                </thead>
                <tbody>
                    ${this.data.list.list.map((item, i) => `
                        <tr>
                            <td>
                                ${this._isManager() ? 
                                    `<a href="#" class="${this.classEditUser}" data-id="${item.id}">${item.firstName} ${item.lastName} (${item.userName})</a>` :
                                    `${item.firstName} ${item.lastName} (${item.userName})`
                                 }
                            </td>
                            ${this._isManager() ?
                                `<td>
                                    ${this._isCurrentUser(item) ? '' : `<a href="#" class="btn btn-danger btn-xs ${this.classDeleteUser}" data-id="${item.id}"><i class="fa fa-times"></i> delete</a>`}
                                </td>`
                                : ''
                            }
                        </tr>
                    `.trim()).join('')}
                </tbody>
            </table>
        `;
    }

    _isManager() {
        return this.data.manager;
    }

    _isCurrentUser(item) {
        return this.data.authUser.id === item.id;
    }

    render(data) {
        this.data = data;
        this.element.innerHTML = this._getHtml();
        this._handleEvents();
    }
}