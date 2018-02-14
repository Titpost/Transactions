
var columnDefs = [
    {headerName: "Account", field: "id", width: 250},
    {headerName: "Ballance", field: "amount", width: 120}
];

var gridOptionsFrom = {
    columnDefs: columnDefs,
    rowSelection: 'single',
    onSelectionChanged: onFromSelectionChanged
};
var gridOptionsTo = {
    columnDefs: columnDefs,
    rowSelection: 'single',
    onSelectionChanged: onToSelectionChanged
};

function onFromSelectionChanged() {
    var selectedRows = gridOptionsFrom.api.getSelectedRows();
    var selectedRowsString = '';
    var amount = 0;
    selectedRows.forEach( function(selectedRow, index) {
        if (index!==0) {
            selectedRowsString += ', ';
        }
        selectedRowsString += selectedRow.id;
        amount += selectedRow.amount;
    });
    document.querySelector('#from').value = selectedRowsString;
    document.querySelector('#amount').value = amount;
}
function onToSelectionChanged() {
    var selectedRows = gridOptionsTo.api.getSelectedRows();
    var selectedRowsString = '';
    selectedRows.forEach( function(selectedRow, index) {
        if (index!==0) {
            selectedRowsString += ', ';
        }
        selectedRowsString += selectedRow.id;
    });
    document.querySelector('#to').value = selectedRowsString;
}

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
    var gridDivFrom = document.querySelector('#myGridFrom');
    new agGrid.Grid(gridDivFrom, gridOptionsFrom);

    var gridDivTo = document.querySelector('#myGridTo');
    new agGrid.Grid(gridDivTo, gridOptionsTo);

    var httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', 'http://localhost:8080/api/account');
    httpRequest.send();
    requestOnReady(httpRequest);
});

function transact() {
    document.querySelector('#status').innerHTML = '<font color="Red">processing...</font>';
    var httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', 'http://localhost:8080/api/account/transact?from='
        + document.querySelector('#from').value
        + '&to='
        + document.querySelector('#to').value
        + '&amount='
        + document.querySelector('#amount').value);
    httpRequest.send();
    requestOnReady(httpRequest);
}

function requestOnReady(httpRequest) {
    httpRequest.onreadystatechange = function() {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            var httpResult = JSON.parse(httpRequest.responseText);
            gridOptionsFrom.api.setRowData(httpResult);
            gridOptionsTo.api.setRowData(httpResult);
        }
        document.querySelector('#status').innerHTML = '<font color="Green">ready</font>';
    };
}