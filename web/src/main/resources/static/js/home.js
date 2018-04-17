$(document).on("click", "#sign_up", function () {
    $("#sign_up_modal").modal("show");
});

function showPass(){
    var x = document.getElementById("pass");
    if (x.type === "password") {
        x.type = "text";
    } else {
        x.type = "password";
    }
}

