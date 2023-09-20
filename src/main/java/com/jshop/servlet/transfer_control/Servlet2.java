package com.jshop.servlet.transfer_control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/serv2")
public class Servlet2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        req.setAttribute("attr1", "serv1");
     //do not call out.close inside hello-world!!!
        req.getRequestDispatcher("/hello-world").include(req, resp);
        out.println("!!!");
        out.println("</body></html>");
        out.close();
    }
}
