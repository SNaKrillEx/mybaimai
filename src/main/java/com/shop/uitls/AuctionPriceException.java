package com.shop.uitls;

//继承父类
public class AuctionPriceException extends Exception{


    //属性 异常信息
    private String massage;

    public AuctionPriceException(String massage) {
        this.massage = massage;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
