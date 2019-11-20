var pass = false;
var idPass = true;
var passwordPass = true;
var emailPass = true;

$('.register').click(function () {
    var id = '.id';
    var password = '.password';
    var email = '.email';

    if(pass === false){
        alert("ID 또는 Email을 확인해주세요.");
        $(id).focus();
        return false;
    }

    if(idPass === false){
        alert("ID를 확인해주세요.");
        $(id).focus();
        return false;
    }

    if(passwordPass === false){
        alert("password를 확인해주세요.");
        $(password).focus();
        return false;
    }
    if(emailPass === false){
        alert("Email을 확인해주세요.");
        $(email).focus();
        return false;
    }

    if($(id).val().trim().length === 0){
        alert("ID를 입력하세요.");
        $(id).focus();
        return false;
    }

    if($(password).val().trim().length === 0){
        alert("PASSWORD를 입력하세요.");
        $(password).focus();
        return false;
    }

    if($(email).val().trim().length === 0){
        alert("EMAIL를 입력하세요.");
        $(email).focus();
        return false;
    }

    var jsonData = JSON.stringify({
        id : $(id).val(),
        password : $(password).val(),
        email : $(email).val()
    });
    $.ajax({
        url: "http://localhost:8080/register",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            alert('등록 성공!');
            location.href = '/login';
        },
        error: function () {
            alert("등록 실패!");
        }
    });
});

$('.id').blur(function () {
    var id = '.id';
    var idErrorMessage = '.idErrorMessage';
    var jsonData = JSON.stringify({
        id : $(id).val()
    });

    if($(id).val().trim().length === 0){
        $(idErrorMessage).html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>필수항목입니다.</p>").show();
        idPass = false;
    }

    else if($(id).val().trim().length <= 3 && $('.id').val().trim().length > 0){
        $(idErrorMessage).html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>ID는 4~12자여야합니다.").show();
        idPass = false;
    }

    else {
        $(idErrorMessage).html("").hide();
        idPass = true;
    }

    $.ajax({
        url: "http://localhost:8080/register/idCheck",
        type: "POST",
        data: jsonData ,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            pass = true;

        },
        error:function (error) {
            pass = false;
            $(idErrorMessage).html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>"+ error.responseText + "</p>").show();
        }

    });
});

$('.password').blur(function () {
    var password = '.password';
    var passwordErrorMessage = '.passwordErrorMessage';
    if($(password).val().trim().length === 0){
        $(passwordErrorMessage).html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>필수항목입니다.</p>").show();
        passwordPass = false;
    }

    else if($(password).val().trim().length <= 3 && $(password).val().trim().length > 0){
        $(passwordErrorMessage).html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>PASSWORD는 4자 이상이어야 합니다.").show();
        passwordPass = false;
    }

    else {
        $(passwordErrorMessage).html("").hide();
        passwordPass = true;
    }
});

$('.email').blur(function () {
    var email = '.email';
    var emailErrorMessage = '.emailErrorMessage';
    var getMail = RegExp(/^[A-Za-z0-9_\.\-]+@[A-Za-z0-9\-]+\.[A-Za-z0-9\-]+/);

    var emailData = JSON.stringify({
        email : $(email).val()
    });

    if($(email).val().trim().length === 0){
        $(emailErrorMessage).html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>필수항목입니다.</p>").show();
        emailPass = false;
    }

    else if(!getMail.test($(email).val())){
        $(emailErrorMessage).html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>이메일 형식이 맞지 않습니다.</p>").show();
        emailPass = false;
    }

    else {
        $(emailErrorMessage).html("").hide();
        emailPass = true;
    }

    $.ajax({
        url: "http://localhost:8080/register/checkEmail",
        type: "POST",
        data: emailData,
        contentType: "application/json",
        dataType: "json",
        success: function () {
            pass = true;
        },
        error:function (error) {
            pass = false;
            $(emailErrorMessage).html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>"+ error.responseText +"</p>").show();

        }

    });

});

