package invoice.invoice_api.model.request.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The Class RequestPostInvoice.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    @Pattern(regexp = "^(\\d{2,4})-?(0?[1-9]|1[0-2])-?(0?[1-9]|[1-2][0-9]|3[0-1])$")
    private String invoiceStartDate;

    /** The invoice end date. */
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^\\d{2,4}-?(0?[1-9]|1[0-2])-?(0?[1-9]|[1-2][0-9]|3[0-1])$")
    private String invoiceEndDate;

    /** The create user. */
    @NotNull
    @NotEmpty
    private String createUser;

}
