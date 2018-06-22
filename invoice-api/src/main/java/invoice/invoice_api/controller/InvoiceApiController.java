package invoice.invoice_api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import invoice.invoice_api.model.exception.InvoiceNoException;
import invoice.invoice_api.model.exception.RequestEntityValidatedException;
import invoice.invoice_api.model.exception.dateException;
import invoice.invoice_api.model.request.dao.RegisterInvoice;
import invoice.invoice_api.model.request.entity.RequestPostInvoice;
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

    /** The register. */
    @Autowired
    RegisterInvoice register;

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

        try {
            Integer.parseInt(invoiceNo);
        } catch (NumberFormatException e) {
            throw new InvoiceNoException(invoiceNo);
        }

        responceDataList = search.searchInvoice(invoiceNo);

        ResponseInvoice responseInvoice = new ResponseInvoice(responceStatusList, responceDataList);
        return responseInvoice;
    }

    /**
     * Adds the invoice.
     *
     * @param postInvoice the post invoice
     * @param bindingResult the binding result
     * @return the response invoice
     */
    @RequestMapping(value = "/", method = { RequestMethod.POST }, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseInvoice addInvoice(@Validated @RequestBody RequestPostInvoice postInvoice,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new RequestEntityValidatedException(bindingResult.getFieldErrors());
        }

        checkStartAndEndDate(postInvoice.getInvoiceStartDate(), postInvoice.getInvoiceEndDate());

        List<InvoiceError> responceStatusList = new ArrayList<InvoiceError>();
        register.registerInvoice(postInvoice);

        ResponseInvoice responseInvoice = new ResponseInvoice(responceStatusList, search.searchLastRegisterInvoice());

        return responseInvoice;
    }

    private boolean checkStartAndEndDate(String startDate, String endDate ) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            Date sDate = dateFormat.parse(startDate);
            Date eDate = dateFormat.parse(endDate);

            if ( sDate.before(eDate)) {
                throw new dateException(startDate, endDate);
            }
        } catch ( ParseException e ) {
            throw new dateException(startDate, endDate);
        }
        return true;
    }


}