package com.coupon.couponChallenge.repository.coupon.impl;

import com.coupon.couponChallenge.dto.coupon.ItemConsultDTO;
import com.coupon.couponChallenge.repository.coupon.CouponRepository;
import com.coupon.couponChallenge.util.constants.CouponConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Log4j2
@Repository
public class CouponRepositoryImpl implements CouponRepository {
    @Override
    public Map<String, Float>  getProductsPrices(List<String> favorite_items){
        Map<String, Float> items = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = "";
        ItemConsultDTO item;
        for(String item_id: favorite_items){
            try {
                url = CouponConstants.Urls.PRODUCT_CONSULT_URL + item_id;
                item = new ItemConsultDTO(restTemplate.getForObject(url, String.class));

                if (Objects.nonNull(item)){
                    items.put(item.getId(), item.getPrice());
                }else {
                    log.info(CouponConstants.Message.CANT_GET_PRODUCT_INFORMATION+ item_id);
                }
            }catch (Exception e){
                log.info(CouponConstants.Message.ERROR_GETTING_PRODUCT_INFORMATION + item_id);
                log.error(e);
            }
        }
        return items;
    }
}
