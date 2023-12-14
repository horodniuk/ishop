package com.jshop.service.impl;

import com.jshop.entity.Order;


import com.jshop.service.NotificationContentBuilderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NotificationContentBuilderServiceImpl implements NotificationContentBuilderService {
    @Value("${app.host}")
    private String host;

    @Override
    public String buildNewOrderCreatedNotificationMessage(Order order) {
        return host + "/order?id=" + order.getId();
    }
}
