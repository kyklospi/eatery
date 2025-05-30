package com.eatery.notification;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Notification Command concrete class for Short Message
 */
@Component
public class SMSCommand {
    // Find your Account SID and Auth Token at twilio.com/console
    // and set the environment variables. See http://twil.io/secure
    @Value("${twilio.account.id}")
    private String ACCOUNT_SID;
    @Value("${twilio.auth.token}")
    private String AUTH_TOKEN;
    @Value("${twilio.phone.number}")
    private String TWILIO_NUMBER;

    public boolean send(String customerPhoneNumber, String text) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                // replace customer number with your own number registered to Twilio account for testing
                new PhoneNumber(customerPhoneNumber),
                new PhoneNumber(TWILIO_NUMBER),
                text
        ).create();

        if (message.getStatus().equals(Message.Status.FAILED) || message.getStatus().equals(Message.Status.CANCELED) ||
                message.getStatus().equals(Message.Status.UNDELIVERED)) {
            return false;
        }

        System.out.println(message.getBody());
        System.out.println(message.getStatus());
        return true;
    }
}
