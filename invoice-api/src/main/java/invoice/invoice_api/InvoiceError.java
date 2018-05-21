package invoice.invoice_api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Class InvoiceError.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceError {

    /** The error code. */
    private String errorCode;

    /** The error message. */
    private String errorMessage;

    /** The error detail. */
    private String errorDetail;
}
