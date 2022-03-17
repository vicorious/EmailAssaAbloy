package com.example.springboot.email;

import com.example.springboot.entity.Email;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmailPort {
    
    void sendEmail(EmailBody emailBody) throws Exception;

    public List<Email> mails() throws Exception;

    void sendComerssiaEmail(EmailBody emailBody) throws Exception;

    void sendFailedComerssiaEmail(EmailBody emailBody) throws Exception;
    
    public Page<Email>listarEmail( String origen, Pageable pageable)throws Exception;
}