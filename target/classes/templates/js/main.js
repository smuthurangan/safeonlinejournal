(function ($) {
    "use strict";
    /*==================================================================
    [ Validate ]*/
    var input = $('.validate-input .input100');

    $('.validate-form').on('submit',function(){
        var check = true;

        for(var i=0; i<input.length; i++) {
            if(validate(input[i]) == false){
                showValidate(input[i]);
                check=false;
            }
        }

        return check;
    });


    $('.validate-form .input100').each(function(){
        $(this).focus(function(){
           hideValidate(this);
        });
    });

    function validate (input) {
        if($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
            if($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
                return false;
            }
        }
        else if($(input).val().trim() == '') {
                return false;
        }
        else if($(input).attr('name') == 'passwordrepeat') {
            if($("input[name=password]").val() != $("input[name=passwordrepeat]").val()) {
                return false;
            }
        } else if($(input).attr('name') == 'newUserName') {
            var formData = {"newUserName":$("input[name=newUserName]").val() }
            var retValue = false;
            $.ajax({
                url : "/checkAlreadyExist",
                type: "POST",
                async: false,
                data : formData,
                success: function(data, textStatus, jqXHR)
                {
                    var dataJson = $.parseJSON(data);
                    if(dataJson.userExist == 'true')
                    {
                        $('#usernamediv').attr('data-validate','Same user name exists. Please enter other user name.');
                        retValue = false;
                    } else {
                        retValue = true;
                    }
                },
                error: function (jqXHR, textStatus, errorThrown)
                {

                }
            });
            return retValue;
        }
    }

    function showEnter() {
        $("#addButton").hide();
        $("#enterJournal").show();
    }

    function showValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).addClass('alert-validate');
    }

    function hideValidate(input) {
        var thisAlert = $(input).parent();

        $(thisAlert).removeClass('alert-validate');
    }
    
    

})(jQuery);