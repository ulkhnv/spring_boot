$(document).ready(function () {
    getUsers();

    $('#table-tab').click(function () {
        getUsers();
    });

    $("#addForm").submit(function () {
        let data = {
            "firstName": $("#fn").val(),
            "lastName": $("#ln").val(),
            "age": $("#age").val(),
            "email": $("#email").val(),
            "password": $("#password").val(),
            "role": $("#role").children("option:selected").val()
        };
        $.ajax({
            type: "POST",
            url: "/admin/saveUser",
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'text',
            success: (function () {
                alert("User successfully added");
                $('#userForm')[0].reset();
            }),
            error: (function () {
                alert("User not added");
            })
        });
    });

    $("#editModal").on("show.bs.modal",function (e) {
        $.ajax({
            url: "/admin/getUsers",
            method: 'GET'
        }).then(function (data) {
            let index = $(e.relatedTarget).data('index');
            let user = data[index];
            $("#idUpdate").attr("value",user.id);
            $("#fnUpdate").attr("value",user.firstName);
            $("#lnUpdate").attr("value",user.lastName);
            $("#ageUpdate").attr("value",user.age);
            $("#emailUpdate").attr("value",user.email);
            $("#passwordUpdate").attr("value",user.password);
        });
    });

    $("#editForm").submit(function () {
        let id = $("#idUpdate").val();
        let data = {
            "id": id,
            "firstName": $("#fnUpdate").val(),
            "lastName": $("#lnUpdate").val(),
            "age": $("#ageUpdate").val(),
            "email": $("#emailUpdate").val(),
            "password": $("#passwordUpdate").val(),
            "role": $("#roleUpdate").children("option:selected").val()
        };
        $.ajax({
            type : "PUT",
            url : "/admin/updateUser/" + id,
            contentType: "application/json",
            data: JSON.stringify(data),
            dataType: 'text'
        });
        return getUsers();
    });

    $("#deleteModal").on("show.bs.modal",function (e) {
        $.ajax({
            url: "/admin/getUsers",
            method: 'GET'
        }).then(function (data) {
            let index = $(e.relatedTarget).data('index');
            let user = data[index];
            $("#idDelete").attr("value",user.id);
            $("#fnDelete").attr("value",user.firstName);
            $("#lnDelete").attr("value",user.lastName);
            $("#ageDelete").attr("value",user.age);
            $("#emailDelete").attr("value",user.email);
            $("#passworDelete").attr("value",user.password);
        });
    });

    $("#deleteForm").submit(function (e) {
        let id = $("#idDelete").val();
        $.ajax({
            url: "/admin/deleteUser"+id,
            method: 'DELETE'
        });
        return getUsers();
    });


});

function getUsers() {
    $.ajax({
        type: "GET",
        url: "/admin/getUsers",
        success: (function (data) {
            let tableBody = '';
            $.each(data, function(index) {
                let user = data[index];
                tableBody += '<tr>';
                tableBody += '<td>' + user.id + '</td>';
                tableBody += '<td>' + user.firstName + '</td>';
                tableBody += '<td>' + user.lastName + '</td>';
                tableBody += '<td>' + user.age + '</td>';
                tableBody += '<td>' + user.email + '</td>';
                tableBody += '<td>' + user.stringRoles + '</td>';
                tableBody += '<td>' + '<button class="btn btn-sm btn-info" type="button" data-toggle="modal" ' +
                    'data-target="#editModal" data-index='+index+'>Edit</button>' + '</td>';
                tableBody += '<td>' + '<button class="btn btn-sm btn-danger" type="button" data-toggle="modal" ' +
                    'data-target="#deleteModal" data-index='+index+'>Delete</button>' + '</td>';
                tableBody += '</tr>';
            });
            $('#tableBody').empty().append(tableBody);
        })
    });
}

