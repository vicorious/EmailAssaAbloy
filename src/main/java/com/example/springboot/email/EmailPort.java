package com.example.springboot.email;

import com.example.springboot.entity.Email;

import java.util.List;

public interface EmailPort {
    void sendEmail(EmailBody emailBody) throws Exception;

    List<Email> mails() throws Exception;

    void sendComerssiaEmail(EmailBody emailBody);
}