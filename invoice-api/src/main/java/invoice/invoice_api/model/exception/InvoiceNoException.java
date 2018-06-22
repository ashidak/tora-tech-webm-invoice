package invoice.invoice_api.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Instantiates a new invoice no exception.
 *
 * @param invoiceNo the invoice no
 */
@AllArgsConstructor

/* (非 Javadoc)
 * @see java.lang.Throwable#toString()
 */
@ToString

/**
 * Gets the invoice no.
 *
 * @return the invoice no
 */
@Getter
public class InvoiceNoException extends RuntimeException{

    /** The invoice no. */
    private String invoiceNo;
}
