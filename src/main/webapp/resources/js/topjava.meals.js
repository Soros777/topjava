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
                    "data": "dateTime"
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
                    "asc"
                ]
            ]
        })
    );
});

function create() {
    $.get(ctx.ajaxUrl + "created", function (data) {
        $('input#dateTime').val(data['dateTime']);
        $('input#description').val(data['description']);
        $('input#calories').val(data['calories']);
    });
    $("#editRow").modal();
}

function update(id, dateTime, description, calories, edit) {
    $('h4#title').html(edit);
    $('input#id').val(id);
    $('input#dateTime').val(dateTime);
    $('input#description').val(description);
    $('input#calories').val(calories);
    $("#editRow").modal();
}

function filter() {
    let urlSuffix = "filter?startDate=" + $('input#startDate').val() +
        "&endDate=" + $('input#endDate').val() + "&startTime=" +
        $('input#startTime').val() + "&endTime=" + $('input#endTime').val();
    $.get(ctx.ajaxUrl + urlSuffix, function (data) {
        ctx.datatableApi.clear().rows.add(data).draw();
    })
}

function saveMeal() {
    const form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        filter();
        successNoty("Saved");
    });
}

function clean() {
    $('input#startDate').val('');
    $('input#startTime').val('');
    $('input#endDate').val('');
    $('input#endTime').val('');
    filter();
}