package com.jshop.servlet.transfer_control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-world")
public class HelloWorld extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
       // String attr1 = (String) req.getAttribute("attr1");
        String attr1 = req.getParameter("attr1");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("Hello world " +attr1);
    }
}
