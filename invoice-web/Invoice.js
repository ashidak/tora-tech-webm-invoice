$(function() {
    
    const requestUrl = "http://localhost:8080/api/invoice/";
    
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
            console.log("エラー：" + textStatus);
            console.log("テキスト：" + jqXHR.responseText);
        })

    $('#invoiceAppendBtn').on('click', function() {
        alert("登録ゥ！！");
    });
    $('#invoiceSearchBtn').on('click', function() {
        
        const requestNo = $("#invoice").val();
        
        $("#invoice_view > tbody").empty();
        
        $.getJSON(
            requestUrl+requestNo,
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
                console.log("エラー：" + textStatus);
                console.log("テキスト：" + jqXHR.responseText);
            })
    });
});