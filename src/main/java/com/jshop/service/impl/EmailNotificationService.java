package com.jshop.service.impl;




import com.jshop.service.NotificationService;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailNotificationService implements NotificationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationService.class);
    private final ExecutorService executorService;

    @Value("${email.smtp.server}")
    private String smtpHost;
    @Value("${email.smtp.port}")
    private String smtpPort;
    @Value("${email.smtp.username}")
    private String smtpUsername;
    @Value("${email.smtp.password}")
    private String smtpPassword;
    @Value("${email.smtp.fromAddress}")
    private String fromEmail;
    @Value("${email.smtp.tryCount}")
    private String tryCount;

    public EmailNotificationService() {
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void sendNotificationMessage(String notificationAddress, String content) {
        executorService.submit(
                   new EmailItem(notificationAddress,
                        "New order",
                                content,
                                Integer.parseInt(tryCount)));
    }

    public void close() {
        executorService.shutdown();
    }

    private class EmailItem implements Runnable {
        private final String emailAddress;
        private final String subject;
        private final String content;
        private int tryCount;

        private EmailItem(String emailAddress, String subject, String content, int tryCount) {
            this.emailAddress = emailAddress;
            this.subject = subject;
            this.content = content;
            this.tryCount = tryCount;
        }

        private boolean isValidTryCount() {
            return tryCount > 0;
        }

        public void run() {
            try {
                SimpleEmail email = new SimpleEmail();
                email.setCharset("utf-8");
                email.setHostName(smtpHost);
                email.setSSLOnConnect(true);
                email.setSslSmtpPort(smtpPort);
                email.setAuthenticator(new DefaultAuthenticator(smtpUsername, smtpPassword));
                email.setFrom(fromEmail);
                email.setSubject(subject);
                email.setMsg(content);
                email.addTo(emailAddress);
                email.send();
            } catch (EmailException e) {
                LOGGER.error("Can't send email: " + e.getMessage(), e);
                tryCount--;
                if (isValidTryCount()) {
                    LOGGER.info("Resend email: {}", this.toString());
                    executorService.submit(this);
                } else {
                    LOGGER.error("Email was not sent: limit of try count");
                }
            } catch (Exception e) {
                LOGGER.error("Error during send email: " + e.getMessage(), e);
            }
        }
    }

}
