package invoice.invoice_web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// TODO: 自動生成された Javadoc
/**
 * The Class InvoiceWebBrowserView.
 */
@Controller
public class InvoiceWebBrowserView {

/**
 * Web.
 *
 * @return the string
 */
@RequestMapping("/web") // (2)
    public String web() {
        return "Invoice";
    }
}

