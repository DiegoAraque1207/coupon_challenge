package com.coupon.couponChallenge.service.coupon;

import com.coupon.couponChallenge.dto.coupon.CouponRequestDTO;
import com.coupon.couponChallenge.dto.coupon.CouponResponseDTO;

import java.util.List;
import java.util.Map;

public interface CouponService {
    List<String> calculate(Map<String, Float> items, Float amount);

    Map<String, Float> getItemsPrice(List<String> favorite_items);
}
