package com.coupon.couponChallenge.dto.coupon;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CouponResponseDTO {
    @NotNull
    private List<String> item_ids;

    @NotNull
    private Float total;

    public List<String> getItem_ids() {
        return item_ids;
    }

    public void setItem_ids(List<String> item_ids) {
        this.item_ids = item_ids;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
