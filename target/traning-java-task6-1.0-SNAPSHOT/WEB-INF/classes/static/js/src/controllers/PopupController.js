export default class PopupController {
    constructor(view, onClose) {
        this.view = view;
        this.onClose = onClose;

        this._handleEvents();
    }

    _handleEvents() {
        this.view.onClose.attach((sender, data) => {
            this.onClose();
        });
    };

    show(title, htmlContent) {
        this.view.render(title, htmlContent);
    }

    closePopup() {
        this.view.closePopup();
    }
}