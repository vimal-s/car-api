package com.udacity.pricing.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.JsonPath;
import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.service.PricingService;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class PricingControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Price> json;

    @MockBean
    private PricingService service;

    @Before
    public void setUp() throws Exception {
        Price price = new Price("INR", BigDecimal.TEN, 1L);
        given(service.getPrice(any())).willReturn(price);
    }

    @Test
    public void getPrice() throws Exception {
        String responseContent = mvc
                .perform(get("/services/price?vehicleId=1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("INR", JsonPath.read(responseContent, "$.currency"));
    }

    @Test
    public void deletePrice() throws Exception {
        mvc
                .perform(delete("/services/price?vehicleId=1"))
                .andExpect(status().isOk());
    }
}
