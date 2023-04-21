window.setTimeout(function () {
    $(".alert").fadeTo(500, 0).slideUp(500, function () {
        $(this).remove();
    });
}, 4000);

$(document).ready(function () {
    $(".link-delete").on("click", function (e) {
        e.preventDefault();
        link = $(this);
        userId = link.attr("userId");
        userName = link.attr("userName");
        $("#btnConfirm").attr("href", link.attr("href"));
        $("#confirmText").text("Are you sure you want to delete " + userName + " (User ID: " + userId + ") ?");
        $("#confirmModal").modal();
    });
});

function setPageSize() {
    num = $("#selectPageSize").val();
    document.getElementById('pageSize').value = num;
    document.getElementById('frmPageSize').submit();
};

$(document).ready(function () {
    $('#fileImage').change(function () {
        fileSize = this.files[0].size;
        if (fileSize > 1048576) {
            $('#uploadModal').modal('show');
            document.getElementById('fileImage').value = "";
        } else {
            showImageThumbnail(this);
        }
    });
});

function showImageThumbnail(fileInput) {
    var file = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function (e) {
        $('#thumbnail').attr("src", e.target.result);
    };
    reader.readAsDataURL(file);
}

function validateForm(form) {
    id = $("#id").val();
    firstName = $("#firstName").val();
    lastName = $("#lastName").val();
    email = $("#email").val();
    password = $("#password").val();
    // passwordConfirm = $("#confirmPassword").val();
    csrfValue = $("input[name='_csrf']").val();
    url = "/posco/users/check_email"; // The word 'posco' in url is depended on the server.servlet.context-path at application.properties
    params = { id: id, email: email, _csrf: csrfValue };
    error = "";
    error += validateFirstName(firstName);
    error += validateLastName(lastName);
    error += validateEmail(email);
    error += validatePassword(id, password);
    // error += validatePasswordMatch(password, passwordConfirm);
    if (error === "") {
        $.post(url, params, function (response) {
            if (response === "Email OK") {
                form.submit();
            }
            else if (response === "Email is existed") {
                document.getElementById("emailHelp").innerHTML
                    = existedEmailMsg;
            }
            else
                alert("Unkown error!!!");
        });
    }
    return false;
};

function validateFirstName(firstName) {
    if (firstName.length < 1 || firstName.length > 50) {
        document.getElementById("firstNameHelp").innerHTML = nameMsg;
        return "Invalid name";
    }
    else {
        document.getElementById("firstNameHelp").innerHTML = "";
        return "";
    }
};

function validateLastName(lastName) {
    if (lastName.length < 1 || lastName.length > 50) {
        document.getElementById("lastNameHelp").innerHTML = nameMsg;
        return "Invalid name";
    }
    else {
        document.getElementById("lastNameHelp").innerHTML = "";
        return "";
    }
};

function validateEmail(email) {
    pattern = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (email == "") {
        document.getElementById("emailHelp").innerHTML = emailMsg;
        return "Invalid email";
    } else if (!pattern.test(email)) {
        document.getElementById("emailHelp").innerHTML = emailMsg;
        return "Invalid email";
    }
    else {
        document.getElementById("emailHelp").innerHTML = "";
        return "";
    }
};

function validatePassword(id, password) {
    pattern = /(?=^.{6,20}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$/;
    if (id != "") {
        if (password.length > 0) {
            if (!pattern.test(password)) {
                document.getElementById("passwordHelp").innerHTML = passwordMsg;
                return "Invalid password";
            }
            else {
                document.getElementById("passwordHelp").innerHTML = "";
                return "";
            }
        } else {
            document.getElementById("passwordHelp").innerHTML = "";
            return "";
        }
    }
    else if (id == "") {     
        if (!pattern.test(password)) {
            document.getElementById("passwordHelp").innerHTML = passwordMsg;
            return "Invalid password";
        }
        else {
            document.getElementById("passwordHelp").innerHTML = "";
            return "";
        }
    }
};

// function validatePasswordMatch(password, passwordConfirm) {
//     if (password != passwordConfirm) {
//         document.getElementById("confirmPasswordHelp").innerHTML = passwordMatchMsg;
//         return "Invalid password";
//     }
//     else {
//         document.getElementById("confirmPasswordHelp").innerHTML = "";
//         return "";
//     }
// };