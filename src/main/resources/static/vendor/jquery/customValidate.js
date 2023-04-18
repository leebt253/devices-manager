function validateForm(form) {
    url = "[[@{/admin/users/check_email}]]";
    userId = $("#id").val();
    userFirstName = $("#firstName").val();
    userLastName = $("#lastName").val();
    userPassword = $("#password").val();
    userPasswordConfirm = $("#confirmPassword").val();
    userEmail = $("#email").val();
    csrfValue = $("input[name='_csrf']").val();
    params = { id: userId, email: userEmail, _csrf: csrfValue };
    $.post(url, params, function (response) {
        if (userFirstName.length < 2 || userFirstName.length > 45) {
            document.getElementById('firstNameHelp').innerHTML
                = 'This field is required.';
                document.getElementById("firstName").style.borderColor="red";
        }
        else{
            document.getElementById('firstNameHelp').innerHTML = '';
            document.getElementById("firstName").style.borderColor="green";
        }
            
        if (userLastName.length < 2 || userLastName.length > 45) {
            document.getElementById('lastNameHelp').innerHTML
                = 'This field is required.';
            document.getElementById("lastName").style.borderColor="red";
        }
        else {
            document.getElementById('lastNameHelp').innerHTML = '';
            document.getElementById("lastName").style.borderColor="green";
        }
            

        if (userPassword != "") {
            if (userPassword.length < 8 || userPassword.length > 20) {
                document.getElementById('passwordHelp').innerHTML
                    = 'Password must be 8 - 20 characters.';
                document.getElementById("password").style.borderColor="red";	
                }
            else {
                document.getElementById('passwordHelp').innerHTML = '';
                document.getElementById("password").style.borderColor="green";	
            }

            if (userPassword != userPasswordConfirm) {
                document.getElementById('confirmPasswordHelp').innerHTML
                    = 'Must be same as password field.';
                document.getElementById("confirmPassword").style.borderColor="red";
            }
            else {
                document.getElementById('confirmPasswordHelp').innerHTML = '';
                document.getElementById("confirmPassword").style.borderColor="red";
            }					
        }
        else if (userPassword == "") {
            document.getElementById('passwordHelp').innerHTML
                = 'This field is required.';
            document.getElementById("password").style.borderColor="red";
            document.getElementById('confirmPasswordHelp').innerHTML
                = 'This field is required.';
            document.getElementById("confirmPassword").style.borderColor="red";
        }
        else {
            document.getElementById('passwordHelp').innerHTML = '';
            document.getElementById("password").style.borderColor="green";
            document.getElementById('confirmPasswordHelp').innerHTML = '';
            document.getElementById("confirmPassword").style.borderColor="green";
        }

        


        // if (response === 'Email is existed') {
        // 	document.getElementById('emailHelp').innerHTML
        // 		= 'That email is existed. Try another.';
        // 	valid = false;
        // }
        // else {
        // 	if (response === 'Email is existed')
        // 	document.getElementById('emailHelp').innerHTML
        // 		= 'That email is existed. Try another.'; 
        // 	else (response === 'Email OK')
        // 		form.submit();
        // }							
    });
    return false;
};