import Event from "../event/Event";

let _instance;

export default class PopupView {
    static getInstance() {
        if (_instance === undefined)
            _instance = new PopupView(document.getElementById('modal-content-past'));
        return _instance;
    }

    constructor(element) {
        this.element = element;
        this.idPopup = "empModal";
        this.classCloseModal = "close-modal";

        this.onClose = new Event(this);
    }

    _handleEvents() {
        this.handleCloseButton();
        this.handleClosePopup();
    }

    handleCloseButton() {
        let closes = document.getElementsByClassName(this.classCloseModal);
        for (let i = 0; i < closes.length; i ++) {
            closes[i].addEventListener('click', (e) => {
                e.preventDefault();
                this.closePopup();
            });
        }
    }

    handleClosePopup() {
        $('#' + this.idPopup).on('hidden.bs.modal', () => {
            this.onClose.notify();
        })
    }

    closePopup() {
        $('#' + this.idPopup).modal('hide');
    }

    showPopup() {
        $('#' + this.idPopup).modal('show');
    }

    _getHtml() {
        return `
            <div class="modal fade" id="${this.idPopup}" role="dialog">
                <div class="modal-dialog">
        
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close ${this.classCloseModal}">&times;</button>
                            <h4 id="title-modal" class="modal-title">${this.title}</h4>
                        </div>
                        <div class="modal-body">
                           ${this.htmlContent}
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="${this.classCloseModal} btn btn-default">Close</button>
                        </div>
                    </div>
                </div>
            </div>`;
    }

    render(title, htmlContent) {
        this.title = title;
        this.htmlContent = htmlContent;

        this.element.innerHTML = this._getHtml();
        this._handleEvents();
        this.showPopup();
    }
}