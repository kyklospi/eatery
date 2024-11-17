package com.eatery.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Command Pattern
 * Invoker of sending notification
 */
@Service
public class NotificationHandler implements NotificationCommand {
    @Autowired
    private SMSCommand smsCommand;
    @Autowired
    private MMSCommand mmsCommand;

    @Override
    public boolean sendSMS(String customerPhoneNumber, String text) {
        return smsCommand.send(customerPhoneNumber, text);
    }

    @Override
    public boolean sendMMS(String customerPhoneNumber, String text, String mediaURL) {
        return mmsCommand.send(customerPhoneNumber, text, mediaURL);
    }
}
