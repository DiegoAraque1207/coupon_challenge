package com.coupon.couponChallenge.repository.coupon.impl;

import com.coupon.couponChallenge.repository.coupon.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CouponRepositoryImplTest {
    @Autowired
    private CouponRepository couponRepository;
    private List<String> ids;
    private List<String> nonValidIds;

    @BeforeEach
    void setup(){
        ids = Arrays.asList(new String []
                {"MLA811601010", "MLA816019440", "MLA19049556", "MLA844702264", "MLA599260060", "MLA594239600"});
        nonValidIds = Arrays.asList(new String []
                {"MLA19049556", "MLA599260060", "MLA594239600"});
    }

    @Test
    void getProductsPrices() {
        try {
            Map<String, Float> productsIdPrices = couponRepository.getProductsPrices(ids);
            assertNotNull(productsIdPrices);
            assertEquals(3, productsIdPrices.size());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Test
    void notItemsFound() {
        try {
            Map<String, Float> productsIdPrices = couponRepository.getProductsPrices(nonValidIds);
            assertNotNull(productsIdPrices);
            assertEquals(0, productsIdPrices.size());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}