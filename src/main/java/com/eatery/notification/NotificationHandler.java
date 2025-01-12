package com.eatery.notification;

import com.eatery.entity.Notification;
import com.eatery.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Command Pattern
 * Invoker of sending notification
 */
@Service
public class NotificationHandler implements NotificationCommand {
    @Value("${notification.enabled}")
    private boolean enabled;
    @Autowired
    private SMSCommand smsCommand;
    @Autowired
    private MMSCommand mmsCommand;
    @Autowired
    private NotificationRepository repository;

    @Override
    public boolean sendSMS(String customerPhoneNumber, String text) {
        if (enabled) {
            return smsCommand.send(customerPhoneNumber, text);
        }
        return true;
    }

    @Override
    public boolean sendMMS(String customerPhoneNumber, String text, String mediaURL) {
        if (enabled) {
            return mmsCommand.send(customerPhoneNumber, text, mediaURL);
        }
        return true;
    }

    public void save(Long customerId, Long reservationId, String message) {
        Notification notification = new Notification(customerId, reservationId, message);
        repository.save(notification);
    }
}
