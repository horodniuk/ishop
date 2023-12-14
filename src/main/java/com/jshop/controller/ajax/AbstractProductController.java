package com.jshop.controller.ajax;

import com.jshop.controller.AbstractController;
import com.jshop.form.ProductForm;
import com.jshop.model.ShoppingCart;
import com.jshop.util.RoutingUtils;
import com.jshop.util.SessionUtils;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractProductController extends AbstractController {

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductForm form = createProductForm(req);
        ShoppingCart shoppingCart = getCurrentShoppingCart(req);
        processProductForm(form, shoppingCart, req, resp);
        if(!SessionUtils.isCurrentShoppingCartCreated(req)) {
            SessionUtils.setCurrentShoppingCart(req, shoppingCart);
        }
        sendResponse(shoppingCart, req, resp);
    }


    private ShoppingCart getCurrentShoppingCart(HttpServletRequest req) {
        ShoppingCart shoppingCart = SessionUtils.getCurrentShoppingCart(req);
        if(shoppingCart == null) {
            shoppingCart = new ShoppingCart();
        }
        return shoppingCart;
    }

    protected abstract void processProductForm(ProductForm form, ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    protected void sendResponse(ShoppingCart shoppingCart, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject cardStatistics = new JSONObject();
        cardStatistics.put("totalCount", shoppingCart.getTotalCount());
        cardStatistics.put("totalCost", shoppingCart.getTotalCost());
        RoutingUtils.sendJSON(cardStatistics, req, resp);
    }
}
