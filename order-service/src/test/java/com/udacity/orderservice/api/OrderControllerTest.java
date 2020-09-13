package com.udacity.orderservice.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.udacity.orderservice.domain.Customer;
import com.udacity.orderservice.domain.Order;
import com.udacity.orderservice.service.OrderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<Order> json;

    @MockBean
    private OrderService service;

    @Before
    public void setUp() {
        Order order = getOrder();
        order.setId(1L);
        given(service.saveOne(any())).willReturn(order);
    }

    @Test
    public void createCar() throws Exception {
        Order order = getOrder();
        mvc
                .perform(post("/orders")
                        .content(json.write(order).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteCar() throws Exception {
        mvc
                .perform(delete("/orders")
                        .param("vehicleId", "1"))
                .andExpect(status().isOk());
    }

    private Order getOrder() {
        Order order = new Order();

        Customer customer = new Customer();
        customer.setFirstname("firstname");
        customer.setLastname("lastname");

        order.setCustomer(customer);
        order.setVehicleId(1L);

        return order;
    }
}
