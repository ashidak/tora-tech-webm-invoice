package invoice.invoice_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import invoice.invoice_api.model.exception.InvoiceNoException;
import invoice.invoice_api.model.exception.RequestEntityValidatedException;
import invoice.invoice_api.model.exception.DateException;
import invoice.invoice_api.model.response.entity.InvoiceError;
import invoice.invoice_api.model.response.entity.InvoiceResult;
import invoice.invoice_api.model.response.entity.ResponseInvoice;

/**
 * The Class ExceptionHandler.
 */
@RestControllerAdvice
public class ExceptionHandler {

    /** The response status list. */
    private List<InvoiceError> responseStatusList = new ArrayList<InvoiceError>();

    /** The response data list. */
    private List<InvoiceResult> responseDataList = new ArrayList<InvoiceResult>();

    /**
     * Invoice no exception handler.
     *
     * @param e the e
     * @return the response invoice
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(InvoiceNoException.class)
    public ResponseInvoice invoiceNoExceptionHandler(InvoiceNoException e) {

        responseStatusList.clear();

        InvoiceError error = new InvoiceError();

        error.setErrorCode("40001");
        error.setErrorMessage("入力した請求書管理番号が不正です。");
        error.setErrorDetail("Error. Input invoiceNo=\"" + e.getInvoiceNo() + "\".");

        responseStatusList.add(error);

        ResponseInvoice responseInvoice = new ResponseInvoice(responseStatusList, responseDataList);
        return responseInvoice;
    }

    /**
     * Request entity validated exception handler.
     *
     * @param e the e
     * @return the response invoice
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(RequestEntityValidatedException.class)
    public ResponseInvoice requestEntityValidatedExceptionHandler(RequestEntityValidatedException e) {

        responseStatusList.clear();

        for (FieldError error : e.getErrors()) {
            InvoiceError responceStatus = new InvoiceError();
            responceStatus.setErrorCode("40002");
            responceStatus.setErrorMessage("入力した登録情報が不正です。");
            responceStatus.setErrorDetail("RequestPostInvoice Field[ " + error.getField() + " ]" +
                    " : " + error.getDefaultMessage() +
                    " value=\"" + error.getRejectedValue() + "\"");

            responseStatusList.add(responceStatus);
        }

        ResponseInvoice responseInvoice = new ResponseInvoice(responseStatusList, responseDataList);
        return responseInvoice;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(DateException.class)
    public ResponseInvoice dateExceptionHandler(DateException e) {

        responseStatusList.clear();

        InvoiceError responceStatus = new InvoiceError();
        responceStatus.setErrorCode("40003");
        responceStatus.setErrorMessage("請求終了日付よりも請求開始日付の方が後の日付です。");
        responceStatus.setErrorDetail("The billing start date is later than the billing end date.");

        responseStatusList.add(responceStatus);

        ResponseInvoice responseInvoice = new ResponseInvoice(responseStatusList, responseDataList);
        return responseInvoice;
    }

}
