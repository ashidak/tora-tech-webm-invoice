package invoice.invoice_api.model.exception;

import java.util.List;

import org.springframework.validation.FieldError;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Instantiates a new request entity validated exception.
 *
 * @param errors the errors
 */
@AllArgsConstructor

/* (非 Javadoc)
 * @see java.lang.Throwable#toString()
 */
@ToString

/**
 * Gets the errors.
 *
 * @return the errors
 */
@Getter
public class RequestEntityValidatedException extends RuntimeException{

    /** The errors. */
    private List<FieldError> errors;
}
