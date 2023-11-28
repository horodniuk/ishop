package com.jshop.service;

import com.jshop.entity.Order;

public interface NotificationService {
    void sendNewOrderCreatedNotification(String notificationAddress, Order order);
}
