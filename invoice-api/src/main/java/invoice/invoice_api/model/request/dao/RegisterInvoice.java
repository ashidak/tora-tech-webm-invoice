package invoice.invoice_api.model.request.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import invoice.invoice_api.model.request.entity.RequestPostInvoice;

/**
 * The Class RegisterInvoice.
 */
@Service
public class RegisterInvoice {

    /** The register jdbc template. */
    @Autowired
    NamedParameterJdbcTemplate registerJdbcTemplate;

    /**
     * Search all invoice.
     *
     * @return the list
     */
    @Transactional
    public void registerInvoice(final RequestPostInvoice postInvoice) {

        final String SearchOrderSql = "INSERT INTO invoice(" +
                "    client_no," +
                "    invoice_status," +
                "    invoice_create_date," +
                "    invoice_title," +
                "    invoice_amt," +
                "    tax_amt," +
                "    invoice_start_date," +
                "    invoice_end_date," +
                "    invoice_note," +
                "    create_user," +
                "    create_datetime," +
                "    update_user," +
                "    update_datetime," +
                "    del_flg" +
                ")" +
                "    SELECT" +
                "       :crientNo," +
                "       10," +
                "        CURRENT_DATE()," +
                "       'title'," +
                "       SUM(o.item_price * o.item_count)," +
                "       SUM(o.item_price * o.item_count) * 0.08," +
                "       :invoiceStartDate," +
                "       :invoiceEndDate," +
                "       'etc'," +
                "       :createUser," +
                "       NOW()," +
                "       :createUser," +
                "       NOW()," +
                "       0" +
                "   FROM" +
                "       ord AS o" +
                "   WHERE" +
                "       o.client_no = :crientNo" +
                "   AND CAST(o.create_datetime AS DATE) >= :invoiceStartDate" +
                "   AND CAST(o.create_datetime AS DATE) <= :invoiceEndDate";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("crientNo", postInvoice.getClientNo())
                .addValue("invoiceStartDate", postInvoice.getInvoiceStartDate())
                .addValue("invoiceEndDate", postInvoice.getInvoiceEndDate())
                .addValue("createUser", postInvoice.getCreateUser());

        registerJdbcTemplate.update(SearchOrderSql, param);

    }

}
