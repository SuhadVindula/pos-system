const tbodyElm = $("#tbl-items tbody");
const modalElm = $('#new-item-modal');
const txtCode =$('#txt-item-code');
const  txtUnitPrice = $('#txt-unit-price');
const  txtDescription = $('#txt-unit-price');
const  txtStock = $('#txt-stock');
const btnSave = $('#btn-save');
tbodyElm.empty();


function generateNewItemCode() {
    if (!tbodyElm.find('tr').length) {
        return 'I001';
    } else {
        let lastCode = tbodyElm.find("tr:last-child").children("td:first-child").text();
        let newCode = (+lastCode.replace('I', '') + 1);
        return `I${newCode.toString().padStart(3, '0')}`;
    }
}

modalElm.on('show.bs.modal',()=>{
    txtCode.val(generateNewItemCode());

});
[txtDescription,txtUnitPrice,txtStock].forEach(txtElm=>$(txtElm.addClass('animat')))
btnSave.on('click', () => {
    if(!validateData()){
        return false;
    };
});

function validateData(){
    let stock = txtStock.val().trim();
    let unitPrice = txtUnitPrice.val().trim();
    let description = txtDescription.val().trim();

    if(!/^\d+$/.test(stock)){
        txtStock.addClass('is-invalid');
    }

}
