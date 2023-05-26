const tbodyElm = $('tbl-items tbody');
const modalElm = $('#new-item-modal');
const txtItemCode = $("#txt-item-code");
const txtDescription = $("#txt-description");
const txtUnitPrice = $("#txt-unit-price");
const txtStock = $("#txt-stock");
const btnSave = $("#btn-save");


tbodyElm.empty();

function generateNewItemCode(){
    if (! tbodyElm.find('tr').length) {
        return'I001'
    }else {
        let lastId = tbodyElm.find('tr:last-child')
            .children('td:first-child').text();
        let newId = +lastId.replace('I', '') + 1;
        return `C${newId.toString().padStart(3,'0')}`;

    }
};

[txtStock, txtDescription, txtStock].forEach(txtElm =>
    $(txtElm).addClass('animate__animated'));

modalElm.on("show.bs.modal",()=>{
    txtItemCode.val(generateNewItemCode());

});

btnSave.on('click', () => {
    if (!validateData()) {
        return false;
    }
});

function validateData() {
    const stock = txtStock.val().trim();
    const unitPrice = txtUnitPrice.val().trim();
    const description = txtDescription.val().trim();
    let valid = true;
    resetForm();

    if (!stock) {
        valid = invalidate(txtStock, "Address can't be empty");
    } else if (!/^\d+$/.test(stock)) {
        valid = invalidate(txtStock, 'Invalid Stock');
    }

    if (!unitPrice) {
        valid = invalidate(txtUnitPrice, "UnitPrice number can't be empty");
    } else if (!/^\d{1,3}(,?\d{3})*(\.\d{1,2})?$/.test(unitPrice)) {
        valid = invalidate(txtUnitPrice, 'Invalid Unit Price number');
    }

    if (!description) {
        valid = invalidate(txtDescription, "Description can't be empty");
    } else if (!/^[A-Za-z ]+$/.test(name)) {
        valid = invalidate(txtDescription, "Invalid Description");
    }

    return valid;
}

function invalidate(txt, msg) {
    setTimeout(() => txt.addClass('is-invalid animate__shakeX'), 0);
    txt.trigger('select');
    txt.next().text(msg);
    return false;
}


function resetForm(clearData) {
    [txtItemCode, txtDescription, txtUnitPrice, txtStock].forEach(txt => {
        txt.removeClass("is-invalid animate__shakeX");
        if (clearData) txt.val('');
    });
}
