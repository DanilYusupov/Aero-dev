$(document).on("click", "#sign_up", function () {
    $("#sign_up_modal").modal("show");
});

$(document).on("click", "#register", function () {
    var name = document.getElementById('new_name').value;
    var email = document.getElementById('new_email').value;
    var password = document.getElementById('new_pass').value;
    var url = "/home?name=" + name + "&email=" + email + "&password=" + password;
    $.post(url, function (id) {
        if (id === 'null'){
            alert("Error while signing up. Try another name or password.");
        } else {
            alert("Signed up successful! Your id: " + id);
            window.location.href = "/user/" + id;
        }
    });
});