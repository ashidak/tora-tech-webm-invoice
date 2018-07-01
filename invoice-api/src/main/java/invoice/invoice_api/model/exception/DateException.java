package invoice.invoice_api.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Instantiates a new date exception.
 *
 * @param startDate the start date
 * @param endDate the end date
 */
@AllArgsConstructor

/* (非 Javadoc)
 * @see java.lang.Object#toString()
 */
@ToString

/**
 * Gets the invoice no.
 *
 * @return the invoice no
 */

/**
 * Gets the end date.
 *
 * @return the end date
 */
@Getter
public class DateException extends RuntimeException {

    /** The start date. */
    private String startDate;

    /** The end date. */
    private String endDate;
}
