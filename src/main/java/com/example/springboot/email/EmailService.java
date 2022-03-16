package com.example.springboot.email;

import com.example.springboot.entity.Email;
import com.example.springboot.repository.EmailRepository;
import com.example.springboot.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService implements EmailPort{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public void sendEmail(EmailBody emailBody) throws Exception {
        sendEmailTool(emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject(), emailBody.getCc(), emailBody.getOrigen());
    }

    @Override
    public List<Email> mails() throws Exception {
        return null;
    }

    @Override
    public void sendComerssiaEmail(EmailBody emailBody)  throws Exception {
        emailBody.setContent(Constantes.HTML);
        sendEmailTool(Constantes.COMERSSIA_EMAIL_DONE+emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject(), emailBody.getCc(), "COMERSSIA");
    }

    @Override
    public void sendFailedComerssiaEmail(EmailBody emailBody) throws Exception {
        emailBody.setContent(Constantes.HTML);
        sendEmailTool(Constantes.COMERSSIA_EMAIL_FAILED+emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject(), emailBody.getCc(), "COMERSSIA");
    }

    /**
     * 
     * @param textMessage
     * @param email
     * @param subject
     * @param cc
     */
    private void sendEmailTool(String textMessage, String email,String subject, String cc, String origen) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String[] to = email.contains(",") ? email.split(",") : new String[]{email};
        String[] cci = cc.contains(",") ? cc.split(",") : new String[]{cc};
        Email emailEn = new Email(email, textMessage, subject, cc, origen);
        helper.setTo(to);
        helper.setCc(cci);
        helper.setText(textMessage, true);
        helper.setSubject(subject);
        sender.send(message);

        this.emailRepository.save(emailEn);

        LOGGER.info("Mail enviado!");


    }




}
