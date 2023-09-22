package com.jshop.listener;


import com.jshop.config.Constants;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import java.util.List;

@WebListener
public class AccountSessionStatisticsListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {}

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        List<String> actions = (List<String>) se.getSession().getAttribute(Constants.ACCOUNT_ACTION_HISTORY);
        if(actions != null){
            logCurrentActionHistory(se.getSession().getId(), actions);
        }
    }

    private void logCurrentActionHistory(String id, List<String> actions) {
        System.out.println(id + " ->\n\t" + String.join("\n\t", actions));
    }
}
