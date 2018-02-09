
var columnDefs = [
    {headerName: "id", field: "id", width: 250},
    {headerName: "amount", field: "amount", width: 120}
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
    selectedRows.forEach( function(selectedRow, index) {
        if (index!==0) {
            selectedRowsString += ', ';
        }
        selectedRowsString += selectedRow.id;
    });
    document.querySelector('#selectedRows').innerHTML = selectedRowsString;
}
function onToSelectionChanged() {
    var selectedRows = gridOptionsTo.api.getSelectedRows();
    var selectedRowsString = '';
    selectedRows.forEach( function(selectedRow, index) {
        if (index!==0) {
            selectedRowsString += ', ';
        }
    });
}

// setup the grid after the page has finished loading
document.addEventListener('DOMContentLoaded', function() {
    var gridDivFrom = document.querySelector('#myGridFrom');
    new agGrid.Grid(gridDivFrom, gridOptionsFrom);

    var gridDivTo = document.querySelector('#myGridTo');
    new agGrid.Grid(gridDivTo, gridOptionsTo);


    // do http request to get our sample data - not using any framework to keep the example self contained.
    // you will probably use a framework like JQuery, Angular or something else to do your HTTP calls.
    var httpRequest = new XMLHttpRequest();
    httpRequest.open('GET', 'http://localhost:8080/api/account');
    httpRequest.send();
    httpRequest.onreadystatechange = function() {
        if (httpRequest.readyState === 4 && httpRequest.status === 200) {
            var httpResult = JSON.parse(httpRequest.responseText);
            gridOptionsFrom.api.setRowData(httpResult);
            gridOptionsTo.api.setRowData(httpResult);
        }
    };
});