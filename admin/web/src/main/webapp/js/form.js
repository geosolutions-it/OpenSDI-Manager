var formUtils = {
    changeAction : function(selector, action) {
        var modal = $(selector);
        var form = modal.find('form');
        form.attr('action', action);
        return form;
    },
    initModalForm : function(selector) {
        var preventSubmit = function(event) {
            if(event.keyCode == 13) {
                
                event.preventDefault();
                //event.stopPropagation();
                return false;
            }
        }
        
        $(selector).keypress(preventSubmit);
        $(selector).on( 'shown',
                        function() {
            $modal = $(this);
            $form = $(this).find('form');
            $form.validate({
                onKeyup : false,
                sendForm : false,
                        eachValidField : function() {
                                            $(this).closest('.control-group')
                                                    .removeClass('error')
                                                    .addClass('success');
                        },
                        eachInvalidField : function() {
                            $(this).closest('.control-group')
                                    .removeClass('success')
                                    .addClass('error');
                        },
                        valid : function() {
                            $.ajax({
                                type : 'POST',
                                url : $($form).attr('action'),
                                data : $($form).serialize(),
                                success : function(response) {
                                    $form.validateDestroy();
                                    $modal.modal('hide');
                                    var $msg = $('<div class="modal modal-in"></div>');
                                    $('#message').remove();
                                    $('body').append(response);
                                                            $('#message').modal('show');
                                                            $('#message').on(
                                                                            'hide',
                                                                            function() {
                                        window.location.reload()
                                    })
                                },
                                                        error : function(response) {
                                    
                                }

                            });
                        }

            });
        });
    }
}
$(function() {

    $('body').on('hidden', '.modal', function() {
        $(this).removeData('modal');
    });
    jQuery.validateExtend({
        equalTo : {
            conditional : function(value) {
                var compareTo = $($(this).attr('data-equalTo'));
                return compareTo.val() == value;
            }
        }
        });

});

function postData(targetId){
    var options = {
            
            target: '#'+targetId
            /*
            success: function(html) {
                //  the next server response could not have the same id
                $("#${containerId}").replaceWith($('#${containerId}', $(html)));
                //$("#${containerId}").html(html);
            }*/
        };

    $(this).submit(options);
    return false;   
}

/**
 * It send ajax request for an URL (default method it's POST) and 
 * put response on targetID replacing string parsed as arguments  
 * (default replacement is remove 'modal hide fade' because it's 
 * usally launched inside a modal window @see csv.jsp)
 **/
function ajaxRequest(targetId, url, method, oldString, replaceWith, data){

    if(url){
        // default str to replace
        var strToReplace = oldString ? oldString : "modal hide fade ";
        var newStr = replaceWith ? replaceWith : "";

        var targetUrl = url ? url : location.href;
        var sendData = data ? data: null;

        if(targetUrl.indexOf("#") == 0){
            // is the form id to obtain action
            targetUrl = $(url)[0].action;
            // obtain data from the form
            if(sendData == null){
                sendData = $(url).serialize();
            }
        }

         var config = {
            async: true,
            type: method ? method: "POST",
            url: targetUrl,
            processData: false,
            data: sendData,
            timeout: 70000,
            cache: false
        };

        $.ajax(config)
            .done(function(html) {
                if(html){
                    $("#"+targetId).replaceWith(html.replace(strToReplace, newStr));
                }else{
                    //console.error("Error in ajax request");
                }
            }).fail(function(response) {
                if(response && response.status == 200 && response.responseText){
                    $("#"+targetId).replaceWith(response.responseText.replace(strToReplace, newStr));
                }else{
                    //console.error("Error in ajax request");
                }
            });

        return true;
    }

    return false;   
}