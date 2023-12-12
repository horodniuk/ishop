package com.jshop.service.impl;

import com.jshop.entity.Order;
import com.jshop.framework.annotation.Component;
import com.jshop.framework.annotation.Value;
import com.jshop.service.NotificationContentBuilderService;

@Component
public class NotificationContentBuilderServiceImpl implements NotificationContentBuilderService {
    @Value("app.host")
    private String host;

    @Override
    public String buildNewOrderCreatedNotificationMessage(Order order) {
        return host + "/order?id=" + order.getId();
    }
}
