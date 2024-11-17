package com.eatery.notification;

/**
 * Command interface
 */
public interface NotificationCommand {
    boolean sendSMS(String customerPhoneNumber, String text);
    boolean sendMMS(String customerPhoneNumber, String text, String mediaURL);
}
