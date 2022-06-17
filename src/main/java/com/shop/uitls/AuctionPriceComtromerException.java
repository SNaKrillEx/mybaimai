package com.shop.uitls;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//异常监听  只要程序异常就会调用
@Component
public class AuctionPriceComtromerException implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {
        AuctionPriceException ex = null;
        //判断系统出现的异常是否   程序员自定义异常
        if (e instanceof AuctionPriceException){
            ex = (AuctionPriceException) e;
        }else {
           ex = new AuctionPriceException("系统异常");
        }

        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMsg",ex.getMassage());
        mv.setViewName("errorPage");
        return mv;
    }
}
