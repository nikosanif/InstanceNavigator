/**
 * Author : Anyfantis Nikolaos 
 * Email: nanifant 'at' ics 'dot' forth 'dot' gr
 * © Copyright 2015 FOUNDATION FOR RESEARCH & TECHNOLOGY - HELLAS
 */
var GlobalResources = {
    
    InstanceService: 'http://'+window.location.hostname+':8080/InstanceNavigator/webresources/instances/get'
    
};





var AjaxRequests = 
{
    GetInstanceData:function()
    {
        var requestType = 'GetInstanceData';
        createLoadingFlag(requestType);
        $.ajax({
            url: GlobalResources.InstanceService,
            dataType: 'json',
            success: function (data) {
                removeLoadingFlag(requestType);
                if(data){
                    try{
                        sessionStorage.InstanceData=JSON.stringify(data);
                    }
                    catch (er){
                        //APPENDError('['+ requestType +'] Instance data too big for cache, '+ er, ERRORSPriority.Medium);
                    }
                    ManageInstanceData(data);
                }
                else{
                    //APPENDError('['+ requestType +'] No Instance data available', ERRORSPriority.High);
                }
            },
            error: function (jqXHR, textStatus) {
                removeLoadingFlag(requestType);
                //APPENDError('['+ requestType +'] No Instance data available, '+textStatus, ERRORSPriority.High);
                console.log( "Request failed for Instance data : " + textStatus );
            }
        });
    }
};



//Creates flag of loading
function createLoadingFlag(requestType){
    var loadingHtml =   '<div class="loadingMessage">\
                            <span>Analyzing...</span>\
                            <i class="fa fa-refresh fa-spin"></i>\
                        </div>';
    
    if(!$('.loadingFromServer .loadingMessage').length>0 ){
        $('.loadingFromServer').append(loadingHtml);
    }
    
    $('.loadingFromServer .requestList').append('<li>'+requestType+'</li>');
}

//Removes flag of loading
function removeLoadingFlag(requestType){
    var requestList = $('.loadingFromServer .requestList li');
    
    $.each(requestList, function(i, r) {
        if($(this).text()===requestType) $(this).remove();
    });
    
    if($('.loadingFromServer .requestList li').length<=0){
        $('.loadingFromServer .loadingMessage').remove();
    }
}
