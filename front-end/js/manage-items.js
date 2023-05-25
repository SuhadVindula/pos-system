const tbodyElm = $('tbl-items tbody');
const modalElm = $('new-item-modal');


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
}

modalElm.on("show.bs.modal",()=>{
    txtId.val(generateNewItemCode());

})