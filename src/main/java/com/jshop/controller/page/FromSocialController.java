package com.jshop.controller.page;

import com.jshop.controller.AbstractController;
import com.jshop.model.CurrentAccount;
import com.jshop.model.SocialAccount;
import com.jshop.util.RoutingUtils;
import com.jshop.util.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;



@WebServlet("/from-social")
public class FromSocialController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code != null) {
            SocialAccount socialAccount = getSocialService().getSocialAccount(code);
            CurrentAccount currentAccount = getOrderService().authentificate(socialAccount);
            SessionUtils.setCurrentAccount(req, currentAccount);
            RoutingUtils.redirect("/my-orders", req, resp);
        } else {
            LOGGER.warn("Parameter code not found");
            RoutingUtils.redirect("/sign-in", req, resp);
        }
    }
}
