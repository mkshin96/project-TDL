$('.id').blur(function () {
    if($('.id').val().trim().length === 0){
        $('.idErrorMessage').html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>필수항목입니다.</p>").show()
        return false;
    }
});

$('.password').blur(function () {
    if($('.password').val().trim().length === 0){
        $('.passwordErrorMessage').html("<p style='color: #DE5E52; font-size: 12px; margin-top: -3px'>필수항목입니다.</p>").show();
        return false;
    }
});
