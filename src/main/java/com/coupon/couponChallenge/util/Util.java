package com.coupon.couponChallenge.util;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class Util {
    private Util (){}

    public static String getJson(Object object) {
        String response = null;
        try {
            response = new Gson().toJson(object);
        } catch (Exception e) {
            log.error("JSON ERROR: ", e);
        }
        return response;
    }
}
