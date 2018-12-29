package com.lazy.tcc.example.dubbo.aggregate.services.retail.controller;

import com.lazy.tcc.example.dubbo.aggregate.services.retail.service.IShopCartService;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.SimpleResponseBuilder;
import com.lazy.tcc.example.dubbo.shared.services.stock.api.dto.SimpleResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * ShopCartController
 * </p>
 *
 * @author laizhiyuan
 * @since 2018/12/11.
 */
@RestController
@RequestMapping("/aggregate/services/retail/shopcart")
public class ShopCartController {

    @Autowired
    private IShopCartService iShopCartService;


    /**
     * Submit Order Api
     *
     * @return {@link SimpleResponseDto}
     */
    @PutMapping("/order")
    public SimpleResponseDto submitOrder() {

        try {

            //submit order
            iShopCartService.submitOrder();
        } catch (Exception ex) {
            ex.printStackTrace();

            return SimpleResponseBuilder.success("no");
        }

        return SimpleResponseBuilder.success("ok");
    }

}
