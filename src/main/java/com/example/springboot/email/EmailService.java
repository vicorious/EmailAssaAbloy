package com.example.springboot.email;

import com.example.springboot.entity.Email;
import com.example.springboot.repository.EmailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService implements EmailPort{

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private EmailRepository emailRepository;

    @Override
    public void sendEmail(EmailBody emailBody) throws Exception {
        sendEmailTool(emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject(), emailBody.getCc());
    }

    @Override
    public void sendComerssiaEmail(EmailBody emailBody)  {
        send(emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject(), emailBody.getCc());

    }

    /**
     * 
     * @param textMessage
     * @param email
     * @param subject
     * @param cc
     */
    private void sendEmailTool(String textMessage, String email,String subject, String cc) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String[] to = email.contains(",") ? email.split(",") : new String[]{email};
        String[] cci = cc.contains(",") ? cc.split(",") : new String[]{cc};
        Email emailEn = new Email(email, textMessage, subject, cc, "MAIL");
        helper.setTo(to);
        helper.setCc(cci);
        helper.setText(textMessage, true);
        helper.setSubject(subject);
        sender.send(message);

        this.emailRepository.save(emailEn);

        LOGGER.info("Mail enviado!");


    }

    /**
     *
     * @param textMessage
     * @param email
     * @param subject
     * @param cc
     */
    private void sendEmailComerssia(String textMessage, String email,String subject, String cc) throws Exception {
        String content = "Asunto: “ESTADO” Orden de traslado N°:xxxxxxxxx Origen: xxxxxxxxx    Destino: xxxxxxxxx.\n" +
                "\n" +
                "Cuerpo del correo:\n" +
                "Se informa que el día de hoy dd/mm/aa 00:00 se ha generado un despacho con los siguientes resultados:\n" +
                "número de orden de traslado: xxxxxxxxxxxxx, desde xxxxxxx, con destino xxxxxxxx, \n" +
                "con las siguientes características:";
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String[] to = email.contains(",") ? email.split(",") : new String[]{email};
        String[] cci = cc.contains(",") ? cc.split(",") : new String[]{cc};
        Email emailEn = new Email(email, textMessage, subject, cc, "COMERSSIA");
        helper.setTo(to);
        helper.setCc(cci);
        helper.setText(textMessage, true);
        helper.setSubject(subject);
        sender.send(message);

        this.emailRepository.save(emailEn);

        LOGGER.info("Mail enviado!");


    }


}
