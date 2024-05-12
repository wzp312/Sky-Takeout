package com.sky.service.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import java.util.List;
public interface ShoppingCartService {
    /*
    * 添加购物车
    * @param shoppingCartDTO
    */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 查看购物车
     * @return */
    List<ShoppingCart> showShoppingCart();

}
