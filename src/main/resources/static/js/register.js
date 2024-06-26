var registerBtn = document.querySelector("#registerBtn");

function register(e) {
    e.preventDefault();

    var email = document.querySelector("#email").value;
    var name = document.querySelector("#name").value;
    var password = document.querySelector("#password").value;

    $.ajax({
        url: '/api/register',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({email: email, name:name,password: password}),
        success: function (data) {
            window.location.href = '/login';
        },
        error: function (xhr, status, error) {
            alert('회원가입에 실패 했습니다.');
        }
    });
}

registerBtn.addEventListener('click',register);
