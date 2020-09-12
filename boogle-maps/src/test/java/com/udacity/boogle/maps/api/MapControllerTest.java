package com.udacity.boogle.maps.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.jayway.jsonpath.JsonPath;
import com.udacity.boogle.maps.domain.address.Address;
import com.udacity.boogle.maps.service.MapService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class MapControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MapService service;

    @Before
    public void setUp() throws Exception {
        Address address = new Address("test-address", "test-city", "test-state", "0000");
        when(service.getAddress(any())).thenReturn(address);
    }

    @Test
    public void getAddress() throws Exception {
        String responseContent = mvc
                .perform(MockMvcRequestBuilders.get("/maps?lat=63.0&lon=33.3"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        assertEquals("test-city", JsonPath.read(responseContent, "$.city"));
        assertEquals("test-state", JsonPath.read(responseContent, "$.state"));
    }
}
