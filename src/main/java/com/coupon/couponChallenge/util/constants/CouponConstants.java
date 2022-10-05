package com.coupon.couponChallenge.util.constants;

public class CouponConstants {
    private CouponConstants() {}

    public static class Paths{
        private Paths(){}
        public static final String COUPON_PATH = "/coupon";
    }

    public static class Urls{
        private Urls(){}
        public static final String PRODUCT_CONSULT_URL = "https://api.mercadolibre.com/items/";
    }

    public static class Operations {
        private Operations(){}

        public static final String CALCULATE_COUPON = "CALCULAR PRODUCTOS DISPONIBLES";
    }

    public static class Message {
        private Message(){}

        public static final String ITEM_NOT_FOUND = "NO SE HA ENCONTRADO PROUDCTOS APLICABLES AL CUPON";
        public static final String START_GETTING_PRODUCTS_PRICES = "INICIA PROCESO PARA OBTENER PRECIOS DE LOS PRODUCTOS";
        public static final String FINISH_GETTING_PRODUCTS_PRICES = "FINALIZA PROCESO PARA OBTENER PRECIOS DE LOS PRODUCTOS";
        public static final String START_CALCULATE_COUPON_PRODUCTS = "INICIA PROCESO PARA LOS PRODUCTOS POSIBLES";
        public static final String FINISH_CALCULATE_COUPON_PRODUCTS = "FINALIZA PROCESO PARA LOS PRODUCTOS POSIBLES";
        public static final String CANT_GET_PRODUCT_INFORMATION = "NO SE HA PODIDO OBTENER LA INFORMACIÓN DEL PRODUCTO: ";
        public static final String ERROR_GETTING_PRODUCT_INFORMATION = "ERROR AL TRAER INFORMACIÓN DEL PRODUCTO: ";

    }
}
