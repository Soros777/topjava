const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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

function changeEnabled(userId, enabled) {
    ctx.ajaxUrl = ctx.ajaxUrl + "change";
    let enb = true;
    if(enabled) {
        enb = false;
    }
    let data = "userId=" + userId + "&enabled=" + enb;

    $.ajax({
        type: "PUT",
        url: ctx.ajaxUrl,
        data: data,
    }).done(function () {
        updateTable();
        successNoty("Enabled changed");
    });
}