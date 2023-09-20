package com.jshop.servlet.login;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;



/*

Создать AdminServlet (‘/admin’), который должен проверить следующие параметры:

Если ip адрес клиента равен IP, то вывести в консоль Login via ip: %s и отобразить клиенту ОК;

Если header[ACCESS_KEY] равен ACCESSKEY, то вывести в консоль Login via accessKey: %s и отобразить клиенту ОК;

Если param[login]=admin, param[password]=password, то вывести в  консоль Login via login and password: %s/%s и отобразить клиенту ОК;

Иначе вывести клиенту FAILED, 401 статус и в консоль отобразить текущее состояние всех данных проверки;

Эталонные параметры задать через Init параметры!

Проверить работу сервлета;
 */


@WebServlet(value = "/admin", initParams={
        @WebInitParam(name="ip", value="22.22.22.22"),
        @WebInitParam(name="ACCESS_KEY", value="secret"),
        @WebInitParam(name="login", value="admin"),
        @WebInitParam(name="password", value="password")
})
public class AdminServlet extends HttpServlet {
    private String ip;
    private String accessKey;
    private String login;
    private String password;

    @Override
    public void init() throws ServletException {
        ip = getServletConfig().getInitParameter("ip");
        accessKey = getServletConfig().getInitParameter("ACCESS_KEY");
        login = getServletConfig().getInitParameter("login");
        password = getServletConfig().getInitParameter("password");
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ip = req.getRemoteAddr();
        String accessKey = req.getHeader("ACCESS_KEY");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        try {
            validate(ip, accessKey, login, password);
            resp.getWriter().println("OK");
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().println("FAILED");
        }
    }

    private void validate(String ip, String accessKey, String login, String password) {
        StringBuilder errors = new StringBuilder();
        if(this.ip.equals(ip)) {
            System.out.println("Login via ip: "+ip);
            return;
        } else {
            errors.append(String.format("Invalid ip: %s\n", ip));
        }
        if(this.accessKey.equals(accessKey)) {
            System.out.println("Login via accessKey: "+accessKey);
            return;
        } else {
            errors.append(String.format("Invalid accessKey: %s\n", accessKey));
        }
        if(this.login.equals(login) && this.password.equals(password)) {
            System.out.println("Login via login and password: "+login+"/"+password);
            return;
        } else {
            errors.append(String.format("Invalid login and(or) password: %s/%s\n", login, password));
        }
        throw new IllegalStateException(errors.toString());
    }
}
