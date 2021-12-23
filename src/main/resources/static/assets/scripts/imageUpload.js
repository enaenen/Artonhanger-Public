(function ($) {
    $(function () {

        var addFormGroup = function (event) {
            event.preventDefault();

            $('.default-ta').remove();
            var $formGroup = $(this).closest('.form-group');
            var $multipleFormGroup = $formGroup.closest('.multiple-form-group');
            var $formGroupClone = $formGroup.clone();

            $(this)
                .toggleClass('btn-default btn-add btn-danger btn-remove')
                .html('–');

            $formGroupClone.find('input').val('');
            $formGroupClone.find('textarea').val('');
            $formGroupClone.find('.preview').attr('src','/assets/img/preview-icon.png');
            $formGroupClone.insertAfter($formGroup);


            var $lastFormGroupLast = $multipleFormGroup.find('.form-group:last');
            if ($multipleFormGroup.data('max') <= countFormGroup($multipleFormGroup)) {
                $lastFormGroupLast.find('.btn-add').attr('disabled', true);
            }
        };

        var removeFormGroup = function (event) {
            event.preventDefault();

            var $formGroup = $(this).closest('.form-group');
            var $multipleFormGroup = $formGroup.closest('.multiple-form-group');

            var $lastFormGroupLast = $multipleFormGroup.find('.form-group:last');
            if ($multipleFormGroup.data('max') >= countFormGroup($multipleFormGroup)) {
                $lastFormGroupLast.find('.btn-add').attr('disabled', false);
            }

            $formGroup.remove();
        };

        var countFormGroup = function ($form) {
            return $form.find('.form-group').length;
        };

        $(document).on('click', '.btn-add', addFormGroup);
        $(document).on('click', '.btn-remove', removeFormGroup);
    });

})(jQuery);
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
    console.log("in over")

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


