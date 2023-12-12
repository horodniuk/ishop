package com.jshop.service;

import com.jshop.entity.Order;

public interface NotificationContentBuilderService {
    String buildNewOrderCreatedNotificationMessage(Order order);
}
