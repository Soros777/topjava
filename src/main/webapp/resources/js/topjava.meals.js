const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});

function create() {
    let date = new Date();
    $('input#dateTime').val(date.toJSON().slice(0,16));
    $("#editRow").modal();
}

function filterMeal() {
    const form = $("#mealsFilter");
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: form.serialize()
    }).done(function (data) {
        fillTable(data);
    });
}