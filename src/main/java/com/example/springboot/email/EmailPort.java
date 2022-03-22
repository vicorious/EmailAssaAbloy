package com.example.springboot.email;

import com.example.springboot.entity.Email;

import java.util.List;

public interface EmailPort {
    
    void sendEmail(EmailBody emailBody) throws Exception;

    List<Email> mails() throws Exception;

    void sendComerssiaEmail(EmailBody emailBody,
                            String traslado,
                            String origen,
                            String destino,
                            String fecha,
                            String numOrder) throws Exception;

    void sendFailedComerssiaEmail(EmailBody emailBody,
                                  String traslado,
                                  String origen,
                                  String fecha,
                                  String destino) throws Exception;
}