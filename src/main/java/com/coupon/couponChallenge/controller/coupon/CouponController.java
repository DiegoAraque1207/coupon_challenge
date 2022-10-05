package com.coupon.couponChallenge.controller.coupon;


import com.coupon.couponChallenge.dto.coupon.CouponRequestDTO;
import com.coupon.couponChallenge.dto.coupon.CouponResponseDTO;
import com.coupon.couponChallenge.service.coupon.CouponService;
import com.coupon.couponChallenge.util.Util;
import com.coupon.couponChallenge.util.constants.Constants;
import com.coupon.couponChallenge.util.constants.CouponConstants;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@RestController
public class CouponController{
    @Autowired
    CouponService couponService;

    @RequestMapping(value = CouponConstants.Paths.COUPON_PATH, method = RequestMethod.POST, produces = {
        "application/json"})
    public ResponseEntity<CouponResponseDTO> getProducts(@Valid @RequestBody CouponRequestDTO request){

        log.info(Constants.Message.START_SERVICE + CouponConstants.Operations.CALCULATE_COUPON);
        log.info("REQUEST -> " + Util.getJson(request));

        CouponResponseDTO response = new CouponResponseDTO();
        List<String> itemsIdPrice;

        try{
            itemsIdPrice = couponService.calculate(
                    couponService.getItemsPrice(request.getItem_ids()), request.getAmount()
            );
        }catch (Exception e){
            log.error(e);
            log.info(Constants.Message.ERROR_SERVICE + CouponConstants.Operations.CALCULATE_COUPON);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if(Objects.nonNull(itemsIdPrice) && !itemsIdPrice.isEmpty()){
            // Para respetar la firma indicada en el reto: List<String> calculate(Map<String, Float> items, Float amount)
            // Se trata la data en el controlador que recibe la petición REST.
            // Como para respetar una arquitectura limpia se puede realizar una mejora en la cual desde el servicio
            // se retorne ya el objeto response contruido con los datos especificados.
            response.setItem_ids(itemsIdPrice.stream()
                    .map(line -> line.split(";")[0])
                    .collect(Collectors.toList()));
            response.setTotal((float) itemsIdPrice.stream()
                    .mapToDouble(line -> Double.parseDouble(line.split(";")[1]))
                    .sum());
        }else{
            log.info(CouponConstants.Message.ITEM_NOT_FOUND);
            log.info(Constants.Message.FINISH_SERVICE + CouponConstants.Operations.CALCULATE_COUPON);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}