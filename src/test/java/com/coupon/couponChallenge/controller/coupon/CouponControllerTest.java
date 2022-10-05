package com.coupon.couponChallenge.controller.coupon;

import com.coupon.couponChallenge.service.coupon.CouponService;
import com.coupon.couponChallenge.util.Util;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.HttpServerErrorException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CouponController.class)
class CouponControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    private List<String> productsIdPrices;
    private Map<String, Object> bodyRequest;

    @BeforeEach
    void setup(){
        productsIdPrices = new ArrayList<>();
        productsIdPrices.add("MLA816019440;116352.03");
        productsIdPrices.add("MLA811601010;19699.0");
        productsIdPrices.add("MLA844702264;1000.0");

        bodyRequest = new HashMap<>();
        bodyRequest.put("item_ids", new String []
                {"MLA811601010", "MLA816019440", "MLA19049556", "MLA844702264", "MLA599260060", "MLA594239600"});
        bodyRequest.put("amount", 5000);

    }

    @Test
    void foundItems() throws Exception {
        when(couponService.calculate(any(), any())).thenReturn(productsIdPrices);

        MvcResult result = mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(Util.getJson(bodyRequest)))
                .andExpect(status().isOk())
                .andReturn();
        String response = result.getResponse().getContentAsString();
        assertNotNull(response);
        assertEquals(true, response.contains("item_ids"));
        assertEquals(true, response.contains("total"));
        assertEquals(false, response.contains("null"));

    }

    @Test
    void notFoundItems() throws Exception {
        when(couponService.calculate(any(), any())).thenReturn(new ArrayList<>());

        MvcResult result = mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(Util.getJson(bodyRequest)))
                .andExpect(status().isNotFound())
                .andReturn();

        String response = result.getResponse().toString();
        assertNotNull(response);
    }

    @Test
    void serverError() throws Exception {
        when(couponService.calculate(any(), any())).thenThrow(HttpServerErrorException.InternalServerError.class);

        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(Util.getJson(bodyRequest)))
                .andExpect(status().isInternalServerError());
    }


    @Test
    void badRequest() throws Exception {
        mockMvc.perform(post("/coupon")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}