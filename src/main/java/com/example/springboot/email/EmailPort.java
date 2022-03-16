package com.example.springboot.email;

public interface EmailPort {
    void sendEmail(EmailBody emailBody) throws Exception;

    void sendComerssiaEmail(EmailBody emailBody);
}