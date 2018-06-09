package invoice.invoice_api.model.request.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * The Class RequestPostInvoice.
 */
@Data
@AllArgsConstructor
@ToString
public class RequestPostInvoice implements Request {

    /** The client no. */
    @NotNull
    @NotEmpty
    @Pattern(regexp = "[0-9]*")
    private String clientNo;

    /** The invoice start date. */
    @NotNull
    @NotEmpty
    private String invoiceStartDate;

    /** The invoice end date. */
    @NotNull
    @NotEmpty
    private String invoiceEndDate;

    /** The create user. */
    @NotNull
    @NotEmpty
    private String createUser;

}
