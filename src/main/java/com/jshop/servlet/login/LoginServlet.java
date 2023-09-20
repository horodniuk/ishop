package com.jshop.servlet.login;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()){
            if(req.getAttribute("error") != null) {
                out.println("<h5 style='color:red'>"+req.getAttribute("error")+"</h5>");
            }
            out.println("<form action='/login' method='post'>");
            out.println("<input name='login' placeholder='Login'>");
            out.println("<input name='password' placeholder='Password' type='password'>");
            out.println("<input type='submit' value='Login'>");
            out.println("</form>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if(isValid(req, login, password)) {
            resp.sendRedirect("/my-private-page");
        } else {
            doGet(req, resp);
        }
    }
    private boolean isValid(HttpServletRequest req, String login, String password) {
        if(login == null || login.trim().isEmpty()) {
            req.setAttribute("error", "Login is required");
            return false;
        }
        if(password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Password is required");
            return false;
        }
        return true;
    }
}
