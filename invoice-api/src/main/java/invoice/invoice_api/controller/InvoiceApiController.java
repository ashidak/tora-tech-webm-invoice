package invoice.invoice_api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invoice.invoice_api.model.response.dao.SearchInvoice;
import invoice.invoice_api.model.response.entity.InvoiceError;
import invoice.invoice_api.model.response.entity.InvoiceResult;
import invoice.invoice_api.model.response.entity.ResponseInvoice;

/**
 * The Class InvoiceApiController.
 */
@RestController
@CrossOrigin
@RequestMapping("/api/invoice")
public class InvoiceApiController {

    /** The search. */
    @Autowired
    SearchInvoice search;

    /**
     * Find all invoice.
     *
     * @return the response invoice
     */
    @RequestMapping("/")
    public ResponseInvoice findAllInvoice() {
        List<InvoiceResult> responceDataList = search.searchAllInvoice();
        ResponseInvoice responseInvoice = new ResponseInvoice(new ArrayList<InvoiceError>(), responceDataList);
        return responseInvoice;
    }

    /**
     * Find invoice.
     *
     * @param invoiceNo the invoice no
     * @return the response invoice
     */
    @RequestMapping("/{invoiceNo}")
    public ResponseInvoice findInvoice(@PathVariable("invoiceNo") String invoiceNo) {

        List<InvoiceResult> responceDataList = new ArrayList<InvoiceResult>();
        List<InvoiceError> responceStatusList = new ArrayList<InvoiceError>();

        InvoiceError error = matchPatternInvoiceNo(invoiceNo);
        if (error != null) {
            responceStatusList.add(error);
        } else {
            responceDataList = search.searchInvoice(invoiceNo);
        }

        ResponseInvoice responseInvoice = new ResponseInvoice(responceStatusList, responceDataList);
        return responseInvoice;
    }

    /**
     * Match pattern invoice no.
     *
     * @param invoiceNo the invoice no
     * @return the invoice error
     */
    private InvoiceError matchPatternInvoiceNo(String invoiceNo) {

        InvoiceError error = new InvoiceError();

        try {
            Integer.parseInt(invoiceNo);
            error = null;

        } catch (NumberFormatException e) {
            error.setErrorCode("40002");
            error.setErrorMessage("入力した請求書管理番号が不正です。");
            error.setErrorDetail("Error. Input invoiceNo=\"" + invoiceNo + "\".");
        }
        return error;
    }
}