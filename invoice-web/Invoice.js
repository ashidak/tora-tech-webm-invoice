$(function() {
    
    const requestUrl = "http://vhost.tora.com:8081/api/invoice/";
    
    $("#invoice_view > tbody").empty();
    
    $.getJSON(
        requestUrl,
        null)
       .success(function(data, status) {
            $.each(data.results, function(index, value) {
                $("#invoice_view > tbody")
                    .append($("<tr></tr>")
                    .append($("<td></td>").text(data.results[index].invoice_no))
                    .append($("<td></td>").text(data.results[index].invoice_status))
                    .append($("<td></td>").text(data.results[index].invoice_amt))
                    .append($("<td></td>").text(data.results[index].invoice_start_date))
                    .append($("<td></td>").text(data.results[index].invoice_end_date))
                    .append($("<td></td>").text(data.results[index].invoice_create_date))
                );
            });
        })
        .error(function(jqXHR, textStatus, errorThrown) {
                const responseData = $.parseJSON(jqXHR.responseText);
                var errorMessage = '';
                
                $.each(responseData.errors, function(index, value) {
                    errorMessage = errorMessage + value.error_code + ":" + value.error_message + "\r\n";
                });
                alert(errorMessage);
        })

    $('#invoiceAppendBtn').on('click', function() {
        $.ajax({
            type:"post",
            url:requestUrl,
            data:JSON.stringify({
                "client_no" : $('#registration_form [name=clientNo]').val(),
                "invoice_start_date" : $('#registration_form [name=invoiceStartDate]').val(),
                "invoice_end_date" : $('#registration_form [name=invoiceEndDate]').val(),
                "create_user" : $('#registration_form [name=createUser]').val()
            }),
            contentType:'application/json; charset=UTF-8',
            dataType:"json",
            success:function(json){
                alert("請求書管理番号" + json.results[0].invoice_no + "を登録しました。");
                location.reload();
            },
            error:function(jqXHR, textStatus, errorThrown){
                const responseData = $.parseJSON(jqXHR.responseText);
                var errorMessage = '';
                
                $.each(responseData.errors, function(index, value) {
                    errorMessage = errorMessage + value.error_code + ":" + value.error_message + "\r\n";
                });
                alert(errorMessage);
            }
        });
    });
    $('#invoiceSearchBtn').on('click', function() {
        
        const requestNo = $("#invoice").val();
        
        $("#invoice_view > tbody").empty();
        
        $.getJSON(
            requestUrl+"/"+requestNo,
            null)
           .success(function(data, status) {
                $.each(data.results, function(index, value) {
                    $("#invoice_view > tbody")
                        .append($("<tr></tr>")
                        .append($("<td></td>").text(data.results[index].invoice_no))
                        .append($("<td></td>").text(data.results[index].invoice_status))
                        .append($("<td></td>").text(data.results[index].invoice_amt))
                        .append($("<td></td>").text(data.results[index].invoice_start_date))
                        .append($("<td></td>").text(data.results[index].invoice_end_date))
                        .append($("<td></td>").text(data.results[index].invoice_create_date))
                    );
                });
            })
            .error(function(jqXHR, textStatus, errorThrown) {
                const responseData = $.parseJSON(jqXHR.responseText);
                var errorMessage = '';
                
                $.each(responseData.errors, function(index, value) {
                    errorMessage = errorMessage + value.error_code + ":" + value.error_message + "\r\n";
                });
                alert(errorMessage);
            })
    });
});