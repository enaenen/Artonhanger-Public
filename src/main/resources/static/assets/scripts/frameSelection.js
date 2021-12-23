var photoFrameWidthTag = "<div class='form-group col-md-5'> " +
    "<label for='frameWidth'>액자 포함 넓이(cm)</label>  " +
    "<input type='number' " +
    "step='any' name='frameWidth' class='form-control' id='frameWidth' " +
    "placeholder='넓이 (cm)' " +
    "required='required'>" +
    "</div>";
var photoFrameHeightTag = "<div class='form-group col-md-5'> " +
    "<label for='frameHeight'>액자 포함 높이(cm)</label>  " +
    "<input type='number' " +
    "step='any' name='frameHeight' class='form-control' id='frameHeight' " +
    "placeholder='높이 (cm)' " +
    "required='required'>" +
    "</div>";
var photoFrameMaterialTag =
    "<div class='form-group'>" +
    "<label for='photoFrameMaterial'>액자 재질</label>" +
    "<input type='text' name='photoFrameMaterial' " +
    "class='form-control' id='photoFrameMaterial' " +
    "placeholder='액자 재질' " +
    "required='required'>" +
    "</div>";

$("#photoFrame").change(function(){
    var selected = $('#photoFrame option:selected').val();
    console.log(selected);
    if (selected === "1"){
        $(this).closest('.form-group');
        $("#photoFrameInfo").append( "<div id=\"photoFrameSize\" class=\"form-group\">" +
            "<div class='row'> " +
            photoFrameHeightTag +
            photoFrameWidthTag +
            "</div>" +
            photoFrameMaterialTag +
            "</div>")
    }
    else if (selected === "0"){
        $('#photoFrameSize').remove();
    }
});
