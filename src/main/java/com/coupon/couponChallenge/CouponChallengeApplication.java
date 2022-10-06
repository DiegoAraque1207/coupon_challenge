package com.coupon.couponChallenge;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Coupon API", version = "1.0.0", description = "Api que calcula la cantidad de productos que se pueden obtener con un cupón"))
public class CouponChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponChallengeApplication.class, args);
	}

}
