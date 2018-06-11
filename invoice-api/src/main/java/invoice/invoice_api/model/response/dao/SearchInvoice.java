package invoice.invoice_api.model.response.dao;

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
public class SearchInvoice {

    /** The jdbc template. */
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

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
     * @return the list
     */
    @Transactional
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
    @Transactional
    public List<InvoiceResult> searchInvoice(final String requestInvoiceNo) {

        StringBuffer sqlBuf = new StringBuffer();

        final String WhereSql = "    WHERE" +
                "    i.invoice_no = :invoiceNo";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("invoiceNo", requestInvoiceNo);

        sqlBuf.append(SearchSql);
        sqlBuf.append(WhereSql);

        return inquiryDataBase(sqlBuf.toString(), param);
    }

    /**
     * Inquiry data base.
     *
     * @param sql the sql
     * @param param the param
     * @return the list
     */
    private List<InvoiceResult> inquiryDataBase(String sql, SqlParameterSource param ) {

        RowMapper<InvoiceResult> mapper = new BeanPropertyRowMapper<InvoiceResult>(InvoiceResult.class);
        List<InvoiceResult> responceDataList = jdbcTemplate.query(sql, param, mapper);
        return responceDataList;
    }
}
