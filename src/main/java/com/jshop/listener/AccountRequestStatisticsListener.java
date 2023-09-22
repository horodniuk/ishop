package com.jshop.listener;


import com.jshop.config.Constants;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebListener
public class AccountRequestStatisticsListener implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {}

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        List<String> action = (List<String>) request.getSession().getAttribute(Constants.ACCOUNT_ACTION_HISTORY);
        if(action == null){
            action = new ArrayList<>();
            request.getSession().setAttribute(Constants.ACCOUNT_ACTION_HISTORY, action);
        }
        action.add(getCurrentAction(request));
    }

    private String getCurrentAction(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(request.getMethod())
                                            .append(" ")
                                            .append(request.getRequestURI());
        Map<String, String[]> map = request.getParameterMap();
        if(map != null){
            boolean first = true;
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                if(first){
                    sb.append('?');
                    first = false;
                } else{
                    sb.append('&');
                }
                for (String value : entry.getValue()) {
                    sb.append(entry.getKey()).append('=').append(value).append('&');
                }
                sb.deleteCharAt(sb.length()-1);
            }
        }
        return sb.toString();
    }
}
