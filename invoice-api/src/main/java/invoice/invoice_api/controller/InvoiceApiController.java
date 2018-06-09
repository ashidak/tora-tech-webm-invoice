package invoice.invoice_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import invoice.invoice_api.model.response.dao.SearchInvoice;
import invoice.invoice_api.model.response.entity.ResponseInvoice;

/**
 * The Class InvoiceApiController.
 */
@RestController
@RequestMapping("/api/invoice")
public class InvoiceApiController {

    @Autowired
    SearchInvoice search;

    /**
     * Find all invoice.
     *
     * @return the response invoice
     */
    @CrossOrigin
    @RequestMapping("/")
    public ResponseInvoice findAllInvoice() {
        return search.searchAllInvoice();
    }

    /**
     * Find all invoice.
     *
     * @return the response invoice
     */
    @CrossOrigin
    @RequestMapping("/{invoiceNo}")
    public ResponseInvoice findInvoice(@PathVariable("invoiceNo") String invoiceNo) {

        /* Integer requestInvoiceNo = Integer.parseInt(invoiceNo); */
        return search.searchInvoice(invoiceNo);
    }
}