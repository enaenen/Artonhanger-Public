(function($) {

  $('#banks').parent().append('<ul class="list-item" id="newbanks" name="banks"></ul>');
  $('#banks option').each(function(){
      $('#newbanks').append('<li value="' + $(this).val() + '">'+$(this).text()+'</li>');
  });
  $('#banks').remove();
  $('#newbanks').attr('id', 'banks');
  $('#banks li').first().addClass('init');
  $("#banks").on("click", ".init", function() {
      $(this).closest("#banks").children('li:not(.init)').toggle();
  });
  
  var allOptions = $("#banks").children('li:not(.init)');
  $("#banks").on("click", "li:not(.init)", function() {
      allOptions.removeClass('selected');
      $(this).addClass('selected');
      $("#banks").children('.init').html($(this).html());
      allOptions.toggle();
  });

  var marginSlider = document.getElementById('slider-margin');
  if (marginSlider != undefined) {
      noUiSlider.create(marginSlider, {
            start: [500],
            step: 10,
            connect: [true, false],
            tooltips: [true],
            range: {
                'min': 0,
                'max': 1000
            },
            format: wNumb({
                decimals: 0,
                thousand: ',',
                prefix: '$ ',
            })
    });
  }
  $('#reset').on('click', function(){
      $('#register-form').reset();
  });

  $('#register-form').validate({
    rules : {
        first_name : {
            required: true,
        },
        last_name : {
            required: true,
        },
        company : {
            required: true
        },
        email : {
            required: true,
            email : true
        },
        phone_number : {
            required: true,
        }
    },
    onfocusout: function(element) {
        $(element).valid();
    },
});

    jQuery.extend(jQuery.validator.messages, {
        required: "",
        remote: "",
        email: "",
        url: "",
        date: "",
        dateISO: "",
        number: "",
        digits: "",
        creditcard: "",
        equalTo: ""
    });
})(jQuery);

$(document).on("click", ".browse", function() {
    var file = $(this).parents().find(".file");
    file.trigger("click");
});
