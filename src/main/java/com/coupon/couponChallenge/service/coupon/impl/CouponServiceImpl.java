package com.coupon.couponChallenge.service.coupon.impl;

import com.coupon.couponChallenge.dto.coupon.ItemConsultDTO;
import com.coupon.couponChallenge.repository.coupon.CouponRepository;
import com.coupon.couponChallenge.service.coupon.CouponService;
import com.coupon.couponChallenge.util.constants.CouponConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class CouponServiceImpl implements CouponService {
    @Autowired
    CouponRepository couponRepository;
    public List<String> calculate(Map<String, Float> items, Float amount){
        log.info(CouponConstants.Message.START_CALCULATE_COUPON_PRODUCTS);
        List<String> prod_items = new ArrayList<>();
        PriorityQueue<ItemConsultDTO> minItemPrice = new PriorityQueue<>();
        for(String key: items.keySet()){
            minItemPrice.add(new ItemConsultDTO(key, items.get(key)));
        }
        float total = 0;
        boolean stillSearching = true;
        ItemConsultDTO tempItem;
        while(stillSearching && !minItemPrice.isEmpty()){
            if (minItemPrice.peek().getPrice() > amount) {
                stillSearching = false;
                continue;
            }
            if (amount < minItemPrice.peek().getPrice() + total) {
                stillSearching = false;
            }else{
                total += minItemPrice.peek().getPrice();
                tempItem = minItemPrice.poll();
                prod_items.add(tempItem.getId() + ";" + tempItem.getPrice());
            }
        }
        log.info(CouponConstants.Message.FINISH_CALCULATE_COUPON_PRODUCTS);
        return prod_items;
    }

    public Map<String, Float> getItemsPrice(List<String> favorite_items){
        log.info(CouponConstants.Message.START_GETTING_PRODUCTS_PRICES);

        Map<String, Float> items = null;
        try {
             items = couponRepository.getProductsPrices(favorite_items.stream()
                     .distinct().collect(Collectors.toList()));
        }catch (Exception e){
            log.info("ERROR GETTING PRODUCTS PRICES");
            log.error(e);
        }
        log.info(CouponConstants.Message.FINISH_GETTING_PRODUCTS_PRICES);
        return items;
    }
}
