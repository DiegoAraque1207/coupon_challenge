package com.coupon.couponChallenge.service.coupon.impl;

import com.coupon.couponChallenge.repository.coupon.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ExtendWith(MockitoExtension.class)

class CouponServiceImplTest {
    @InjectMocks
    CouponServiceImpl couponService;
    @Mock
    private CouponRepository couponRepository;

    private Map<String, Float> productsIdPrices;
    private List<String> ids;

    @BeforeEach
    void setup(){
        productsIdPrices = new HashMap<>();
        productsIdPrices.put("MLA816019440", (float) 116352.03);
        productsIdPrices.put("MLA811601010", (float) 19699.0);
        productsIdPrices.put("MLA844702264", (float) 1000.0);

        ids = Arrays.asList(new String []
                {"MLA811601010", "MLA816019440", "MLA19049556", "MLA844702264", "MLA599260060", "MLA594239600"});
    }
    @Test
    void calculate() {
        try {
            List<String> result = couponService.calculate(productsIdPrices, (float) 5000);
            assertNotNull(result);
            assertEquals("MLA844702264;1000.0", result.get(0));
            assertEquals(1, result.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void calculateNotFound() {
        try {
            List<String> result = couponService.calculate(productsIdPrices, (float) 500);
            assertNotNull(result);
            assertEquals(0, result.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getItemsPrice() {
        when(couponRepository.getProductsPrices(ids)).thenReturn(productsIdPrices);
        try {
            Map<String, Float>  productPrices = couponService.getItemsPrice(ids);
            assertNotNull(productPrices);
            assertEquals(3, productPrices.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    @Test
    void getItemsPriceError() {
        try {
            Map<String, Float>  productPrices = couponService.getItemsPrice(null);
             assertNull(productPrices);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}