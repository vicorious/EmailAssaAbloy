package com.example.springboot.email;

import com.example.springboot.entity.Email;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailPort {
    
    void sendEmail(EmailBody emailBody) throws Exception;

    Object saveToken(String token) throws Exception;

    Page<Email> mails(Pageable pageable) throws Exception;

    void sendComerssiaEmail(EmailBody emailBody,
                            List<EmailDone> emailDones) throws Exception;

    void sendFailedComerssiaEmail(EmailBody emailBody,
                                  List<EmailDone> emailDones) throws Exception;

                                  
    Page<Email> listarEmail(String origen, Pageable pageable) throws Exception;
}