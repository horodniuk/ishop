package com.jshop.controller.page;


import com.jshop.config.Constants;
import com.jshop.controller.AbstractController;
import com.jshop.util.RoutingUtils;
import com.jshop.util.SessionUtils;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/sign-in")
public class SignInController extends AbstractController {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SessionUtils.isCurrentAccountCreated(req)) {
            RoutingUtils.redirect("/my-orders", req, resp);
        } else {
            RoutingUtils.forwardToPage("sign-in.jsp", req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (SessionUtils.isCurrentAccountCreated(req)) {
            RoutingUtils.redirect("/my-orders", req, resp);
        } else {
            String targetUrl = req.getParameter("target");
            if (targetUrl != null) {
                req.getSession().setAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN, targetUrl);
            }
            RoutingUtils.redirect(getSocialService().getAuthorizeUrl(), req, resp);
        }
    }
}
