function readURL(input) {
    var sizeChkResult = sizeCheck(input);
    var sizeChkMsg = sizeChkResult;
    if(sizeChkMsg != "Y") {
        toastError(sizeChkMsg);
    }

    var $inputTag = input;
    var $imgPreview = $($inputTag).closest('.form-group').find('.preview');
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $imgPreview
                .attr('src', e.target.result);
        };

        reader.readAsDataURL(input.files[0]);
    }
}
function sizeCheck(input){
    var fileSize = input.files[0].size;
    var maxSize = 1024 * 1024 * 10; // 10MB

    if (fileSize > maxSize) {
        var msg = maxSize / 1048576 + "MB 이하의 파일만 업로드 가능합니다.";
        return msg;
    }
    else{
        return "Y";
    }
}