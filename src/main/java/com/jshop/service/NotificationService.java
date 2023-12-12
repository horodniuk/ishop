package com.jshop.service;

import com.jshop.entity.Order;

public interface NotificationService {
    void sendNotificationMessage(String notificationAddress, String content);
}
