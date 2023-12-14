package com.jshop.controller.ajax;

import com.jshop.controller.AbstractController;
import com.jshop.form.ProductForm;
import com.jshop.model.ShoppingCart;
import com.jshop.util.RoutingUtils;
import com.jshop.util.SessionUtils;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/ajax/json/product/add")
public class AddProductController extends AbstractProductController {

    @Override
    protected void processProductForm(ProductForm form, ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getOrderService().addProductToShoppingCart(form, shoppingCart);
        String cookieValue = getOrderService().serializeShoppingCart(shoppingCart);
        SessionUtils.updateCurrentShoppingCartCookie(cookieValue, resp);
    }
}
