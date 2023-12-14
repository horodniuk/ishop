package com.jshop.controller.page;

import com.jshop.config.Constants;
import com.jshop.controller.AbstractController;
import com.jshop.model.CurrentAccount;
import com.jshop.model.SocialAccount;
import com.jshop.util.RoutingUtils;
import com.jshop.util.SessionUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;


@WebServlet("/login/oauth2/code/google")
public class FromSocialController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String code = req.getParameter("code");
        if (code != null) {
            SocialAccount socialAccount = getSocialService().getSocialAccount(code);
            CurrentAccount currentAccount = getOrderService().authentificate(socialAccount);
            SessionUtils.setCurrentAccount(req, currentAccount);
            redirectToSuccessPage(req, resp);
        } else {
            LOGGER.warn("Parameter code not found");
            if(req.getSession().getAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN) != null){
                req.getSession().removeAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN);
            }
            RoutingUtils.redirect("/sign-in", req, resp);
        }
    }

    protected void redirectToSuccessPage(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String targetUrl = (String) req.getSession().getAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN);
        if (targetUrl != null) {
            req.getSession().removeAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN);
            RoutingUtils.redirect(URLDecoder.decode(targetUrl, "UTF-8"), req, resp);
        } else {
            RoutingUtils.redirect("/my-orders", req, resp);
        }
    }
}

