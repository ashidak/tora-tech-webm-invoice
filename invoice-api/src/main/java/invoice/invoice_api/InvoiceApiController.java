package invoice.invoice_api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO: 自動生成された Javadoc
/**
 * The Class InvoiceApiController.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/api/invoice")
public class InvoiceApiController {

    /** The jdbc template. */
    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Find all invoice.
     *
     * @return the list
     */
    @RequestMapping("/")
    public List<InvoiceResult> findAllInvoice() {

        RowMapper<InvoiceResult> mapper = new BeanPropertyRowMapper<InvoiceResult>(InvoiceResult.class);
        String sql = "SELECT "
                + "i.invoice_no as invoiceNo, c.client_name as clientName, c.client_address as clientAddress,"
                + "c.client_tel as clientTel, c.client_fax as clientFax,"
                + "CONCAT(c.client_charge_last_name,c.client_charge_first_name ) as clientChargeName,"
                + "i.invoice_status as invoiceStatus, i.invoice_create_date as invoiceCreateDate,"
                + "i.invoice_title as invoiceTitle, i.invoice_amt as invoiceAmt, i.tax_amt as taxAmt,"
                + "i.invoice_start_date as invoiceStartDate, i.invoice_end_date as invoiceEndDate,"
                + "i.invoice_note as invoiceNote, i.create_user as createUser, c.create_datetime as createDatetime,"
                + "i.update_user as updateUser, i.update_datetime as updateDatetime "
                + "FROM invoice as i "
                + "JOIN client as c "
                + "ON i.client_no = c.client_no "
                + "JOIN ord as o "
                + "ON c.client_no = o.client_no;";

        //        List<InvoiceResult> respoceList = new ArrayList<InvoiceResult>();
        List<InvoiceResult> respoceList = jdbcTemplate.query(sql, mapper);

        //        respoceList.add((InvoiceResult) jdbcTemplate.query(sql, mapper));

        return respoceList;
    }
}