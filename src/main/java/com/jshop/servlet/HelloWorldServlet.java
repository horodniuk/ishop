package com.jshop.servlet;


import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class HelloWorldServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cookie c = new Cookie("author", "horodnuik");
        c.setMaxAge(1800); // if negative,the cookie is not stored; if zero, deletes the cookie
        c.setPath("/");
        c.setHttpOnly(true);
        resp.addCookie(c);

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("Hello world!");
        out.println("</body></html>");
        out.close();
    }
}
