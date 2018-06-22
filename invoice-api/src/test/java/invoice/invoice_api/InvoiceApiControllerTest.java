package invoice.invoice_api;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import invoice.invoice_api.model.response.entity.InvoiceError;
import invoice.invoice_api.model.response.entity.InvoiceResult;
import invoice.invoice_api.model.response.entity.ResponseInvoice;

@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceApiApplication.class)
public class InvoiceApiControllerTest {

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mvc;

    @Before
    public void before() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(new InvoiceApiApplication()).build();
    }


    @Test
    public void testGet__Ok() throws Exception {

        Integer id = 26;

        List<InvoiceResult> resultList = new ArrayList<InvoiceResult>();
        InvoiceResult result = new InvoiceResult();

        result.setInvoiceNo(id.toString());
        result.setClientName("クライアント名");
        result.setClientAddress("顧客住所");
        result.setClientTel("03-1234-5678");
        result.setClientFax("03-1234-5678");
        result.setInvoiceStatus("20");
        result.setInvoiceCreateDate("2018-01-13");
        result.setInvoiceTitle("title");
        result.setInvoiceAmt("100");
        result.setTaxAmt("8");
        result.setInvoiceStartDate("2018-01-13");
        result.setInvoiceEndDate("2018-01-13");
        result.setInvoiceNote("note");
        result.setCreateUser("createUser");
        result.setCreateDatetime("2018-01-05 19:25:31.0");
        result.setUpdateUser("updateUser");
        result.setUpdateDatetime("2018-01-13 01:35:19.0");

        resultList.add(result);

        mvc.perform(MockMvcRequestBuilders.get("/api/invoice/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().json(mapper.writeValueAsString(new ResponseInvoice(new ArrayList<InvoiceError>(), resultList))));
    }

    @Test
    public void testGet__NG() throws Exception {

        String id = "asd";

        List<InvoiceError> errorList = new ArrayList<InvoiceError>();
        InvoiceError error = new InvoiceError();

        error.setErrorCode("40001");
        error.setErrorDetail("入力した請求書管理番号が不正です。");
        error.setErrorMessage("Error. Input invoiceNo=\"asd\".");
        errorList.add(error);

        mvc.perform(MockMvcRequestBuilders
                .get("/api/invoice/{id}", id))
        .andExpect(status().is4xxClientError())
        .andExpect(content().json(mapper.writeValueAsString(new ResponseInvoice(errorList, new ArrayList<InvoiceResult>()))));
    }

    @Test
    public void testGet__Error() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/api/invo"))
        .andExpect(status().is5xxServerError());
    }

}
