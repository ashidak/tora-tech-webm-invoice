package invoice.invoice_api.model.response.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import invoice.invoice_api.model.response.entity.InvoiceError;
import invoice.invoice_api.model.response.entity.InvoiceResult;
import invoice.invoice_api.model.response.entity.ResponseInvoice;

/**
 * The Class SearchInvoice.
 */
@Service
public class SearchInvoice {

    /** The jdbc template. */
    @Autowired
    JdbcTemplate jdbcTemplate;

    /** The Constant SearchSql. */
    final static String SearchSql = "SELECT" +
            "    i.invoice_no," +
            "    c.client_name," +
            "    c.client_address," +
            "    c.client_tel," +
            "    c.client_fax," +
            "    CONCAT(c.client_charge_last_name, c.client_charge_first_name)," +
            "    i.invoice_status," +
            "    i.invoice_create_date," +
            "    i.invoice_title," +
            "    i.invoice_amt," +
            "    i.tax_amt," +
            "    i.invoice_start_date," +
            "    i.invoice_end_date," +
            "    i.invoice_note," +
            "    i.create_user," +
            "    c.create_datetime," +
            "    i.update_user," +
            "    i.update_datetime" +
            "    FROM" +
            "    invoice as i" +
            "    JOIN" +
            "        client as c" +
            "    ON  i.client_no = c.client_no" +
            "    JOIN" +
            "        ord as o" +
            "    ON  c.client_no = o.client_no";

    /**
     * Search all invoice.
     *
     * @return the response invoice
     */
    @Transactional
    public ResponseInvoice searchAllInvoice() {
        return createResponse(SearchSql);
    }

    /**
     * Search invoice.
     *
     * @param invoiceNo the invoice no
     * @return the response invoice
     */
    @Transactional
    public ResponseInvoice searchInvoice(String invoiceNo) {

        Integer requestInvoiceNo;
        InvoiceError error = new InvoiceError();

        StringBuffer sqlBuf = new StringBuffer();

        try {
            requestInvoiceNo = Integer.parseInt(invoiceNo);

            final String WhereSql = "    WHERE" +
                    "    i.invoice_no = " + requestInvoiceNo;

            sqlBuf.append(SearchSql);
            sqlBuf.append(WhereSql);

            error = null;

        } catch (NumberFormatException e) {
            error.setErrorCode("40002");
            error.setErrorMessage("入力した請求書管理番号が不正です。");
            error.setErrorDetail("Error. Input invoiceNo=\"" + invoiceNo + "\".");
        }

        return createResponse(sqlBuf.toString(), error);
    }

    /**
     * Creates the response.
     *
     * @param sql the sql
     * @param error the error
     * @return the response invoice
     */
    private ResponseInvoice createResponse(String sql, InvoiceError error) {

        List<InvoiceError> responceStatusList = new ArrayList<InvoiceError>();
        List<InvoiceResult> responceDataList;

        if (error != null) {
            responceStatusList.add(error);
            responceDataList = new ArrayList<InvoiceResult>();

        } else {
            responceDataList = inquiryDataBase(sql);

        }
        ResponseInvoice response = new ResponseInvoice(responceStatusList, responceDataList);
        return response;
    }

    /**
     * Creates the response.
     *
     * @param sql the sql
     * @return the response invoice
     */
    private ResponseInvoice createResponse(String sql) {

        InvoiceError error = null;
        return createResponse(sql, error);
    }

    /**
     * Inquiry data base.
     *
     * @param sql the sql
     * @return the list
     */
    private List<InvoiceResult> inquiryDataBase(String sql) {

        RowMapper<InvoiceResult> mapper = new BeanPropertyRowMapper<InvoiceResult>(InvoiceResult.class);
        List<InvoiceResult> responceDataList = jdbcTemplate.query(sql, mapper);
        return responceDataList;
    }
}
