package invoice.invoice_api.model.response.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import invoice.invoice_api.model.response.entity.InvoiceResult;

/**
 * The Class SearchInvoice.
 */
@Service
@Transactional(readOnly=true)
public class SearchInvoice {

    /** The search jdbc template. */
    @Autowired
    NamedParameterJdbcTemplate searchJdbcTemplate;

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
            "    ON  i.client_no = c.client_no";

    /**
     * Search all invoice.
     *
     * @return the list
     */
    public List<InvoiceResult> searchAllInvoice() {
        SqlParameterSource param = new MapSqlParameterSource();
        return inquiryDataBase(SearchSql, param);
    }

    /**
     * Search invoice.
     *
     * @param requestInvoiceNo the request invoice no
     * @return the list
     */
    public List<InvoiceResult> searchInvoice(final String requestInvoiceNo) {

        StringBuffer sqlBuf = new StringBuffer();

        final String WhereSql = "    WHERE" +
                "    i.invoice_no = :invoiceNo";

        sqlBuf.append(SearchSql);
        sqlBuf.append(WhereSql);

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("invoiceNo", requestInvoiceNo);

        return inquiryDataBase(sqlBuf.toString(), param);
    }

    /**
     * Search last register invoice.
     *
     * @return the list
     */
    public List<InvoiceResult> searchLastRegisterInvoice() {

        List<InvoiceResult> responceDataList = new ArrayList<InvoiceResult>();
        SqlParameterSource param = new MapSqlParameterSource();

        Integer registerInvoiceNo = searchJdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", param, Integer.class);
        System.out.println(registerInvoiceNo);

        InvoiceResult invoiceResult = new InvoiceResult();
        invoiceResult.setInvoiceNo(registerInvoiceNo.toString());

        responceDataList.add(invoiceResult);

        return responceDataList;
    }

    /**
     * Inquiry data base.
     *
     * @param sql the sql
     * @param param the param
     * @return the list
     */
    private List<InvoiceResult> inquiryDataBase(String sql, SqlParameterSource param) {

        RowMapper<InvoiceResult> mapper = new BeanPropertyRowMapper<InvoiceResult>(InvoiceResult.class);
        List<InvoiceResult> responceDataList = searchJdbcTemplate.query(sql, param, mapper);

        return responceDataList;
    }
}
