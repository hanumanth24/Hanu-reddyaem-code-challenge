$(document).ready(function() {
    $("#new_form").on('submit', function(e) {
        e.preventDefault();
        var arr = {
            firstName: $("#new_form input[name=firstName]").val(),
            lastName: $("#new_form input[name=lastName]").val(),
            country: $('.country__cmp')[0].innerHTML,
            age: $("#new_form input[name=age]").val()
        };
        $.ajax({
            url: '/bin/saveUserDetails',
            type: 'POST',
            data: JSON.stringify(arr),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            async: false,
            success: function(msg) {
                alert(msg);
            }
        });

    });
});