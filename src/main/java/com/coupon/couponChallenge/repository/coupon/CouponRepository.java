package com.coupon.couponChallenge.repository.coupon;

import java.util.List;
import java.util.Map;

public interface CouponRepository {
    Map<String, Float> getProductsPrices(List<String> favorite_items);
}
