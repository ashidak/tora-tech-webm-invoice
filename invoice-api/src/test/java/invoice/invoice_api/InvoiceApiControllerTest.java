package invoice.invoice_api;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import invoice.invoice_api.controller.InvoiceApiController;
import invoice.invoice_api.model.request.dao.RegisterInvoice;
import invoice.invoice_api.model.request.entity.RequestPostInvoice;
import invoice.invoice_api.model.response.dao.SearchInvoice;
import invoice.invoice_api.model.response.entity.InvoiceError;
import invoice.invoice_api.model.response.entity.InvoiceResult;
import invoice.invoice_api.model.response.entity.ResponseInvoice;

/**
 * The Class InvoiceApiControllerTest.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(InvoiceApiApplication.class)
@WebAppConfiguration
public class InvoiceApiControllerTest {

    /** The rule. */
    @Rule
    public final MockitoRule rule = MockitoJUnit.rule();

    /** The controller. */
    @InjectMocks
    private InvoiceApiController controller;

    /** The search. */
    @Mock
    private SearchInvoice search;

    /** The register. */
    @Mock
    private RegisterInvoice register;

    /** The mapper. */
    @Autowired
    private ObjectMapper mapper;

    /** The mvc. */
    private MockMvc mvc;

    /** The handler exception resolver. */
    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    /**
     * Before.
     *
     * @throws Exception the exception
     */
    @Before
    public void before() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(controller).setHandlerExceptionResolvers(handlerExceptionResolver)
                .build();
    }

    /**
     * Test get ok find all invoice.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGet_Ok_FindAllInvoice() throws Exception {

        InvoiceResult result = new InvoiceResult(
                "26", "クライアント名", "顧客住所", "03-1234-5678", "03-1234-5678", "顧客名字顧客名前", "20", "2018-01-13", "title",
                "100", "8", "2018-01-13", "2018-01-13", "note", "createUser", "2018-01-05 19:25:31.0", "updateUser",
                "2018-01-13 01:35:19.0");

        List<InvoiceResult> resultList = new ArrayList<InvoiceResult>();
        resultList.add(result);

        when(search.searchAllInvoice()).thenReturn(resultList);

        mvc.perform(MockMvcRequestBuilders.get("/api/invoice/")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().json(
                mapper.writeValueAsString(new ResponseInvoice(new ArrayList<InvoiceError>(), resultList))));

    }

    /**
     * Test get ok find invoice.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGet_Ok_FindInvoice() throws Exception {

        InvoiceResult result = new InvoiceResult();

        result.setInvoiceNo("26");
        result.setClientName("クライアント名");
        result.setClientAddress("顧客住所");
        result.setClientTel("03-1234-5678");
        result.setClientFax("03-1234-5678");
        result.setClientChargeName("顧客名字顧客名前");
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

        List<InvoiceResult> resultList = new ArrayList<InvoiceResult>();
        resultList.add(result);

        when(search.searchInvoice("26")).thenReturn(resultList);

        mvc.perform(MockMvcRequestBuilders.get("/api/invoice/26")).andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(content().json(
                mapper.writeValueAsString(new ResponseInvoice(new ArrayList<InvoiceError>(), resultList))));

    }

    /**
     * Test get N G find invoice.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGet_NG_FindInvoice() throws Exception {

        List<InvoiceError> errorList = new ArrayList<InvoiceError>();
        InvoiceError error = new InvoiceError();

        error.setErrorCode("40001");
        error.setErrorMessage("入力した請求書管理番号が不正です。");
        error.setErrorDetail("Error. Input invoiceNo=\"asd\".");
        errorList.add(error);

        mvc.perform(MockMvcRequestBuilders.get("/api/invoice/asd")).andExpect(status().isBadRequest())
        .andExpect(content().json(
                mapper.writeValueAsString(new ResponseInvoice(errorList, new ArrayList<InvoiceResult>()))));
    }

    /**
     * Test get error find invoice.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGet_Error_FindInvoice() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("")).andExpect(status().isNotFound());
    }

    /**
     * Test post N G add invoice 01.
     *
     * @throws Exception the exception
     */
    @Test
    public void testPost_NG_AddInvoice_01() throws Exception {

        RequestPostInvoice postInvoice = new RequestPostInvoice();
        postInvoice.setClientNo("20001");
        //postInvoice.setCreateUser("");
        postInvoice.setInvoiceStartDate("2018-01-01");
        postInvoice.setInvoiceEndDate("2018-01-31");

        mvc.perform(MockMvcRequestBuilders.post("/api/invoice/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(postInvoice)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}
