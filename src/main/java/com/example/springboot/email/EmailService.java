package com.example.springboot.email;

import com.example.springboot.entity.Email;
import com.example.springboot.entity.Token;
import com.example.springboot.repository.EmailRepository;
import com.example.springboot.repository.TokenRepository;
import com.example.springboot.utils.Constantes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmailService implements EmailPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private TokenRepository tokenRepository;    

    @Override
    public void sendEmail(EmailBody emailBody) throws Exception {
        sendEmailTool(emailBody.getContent(),emailBody.getEmail(), emailBody.getSubject(), emailBody.getCc(), emailBody.getOrigen());
    }

    @Override
    public Page<Email> mails(Pageable pageable) throws Exception {    	    
        return this.emailRepository.listarNotificacionesWithOut(pageable);
    }

    @Override
    public void sendComerssiaEmail(EmailBody emailBody, List<EmailDone> emailDones)  throws Exception {
        String contendo = String.format(emailBody.getContent(), new Date().toString(), emailDones.get(0).getTraslado(), emailDones.get(0).getOrigen(), emailDones.get(0).getDestino());
        System.out.println(contendo);
        emailBody.setContent(String.format(emailBody.getContent(), new Date().toString(), emailDones.get(0).getTraslado(), emailDones.get(0).getOrigen(), emailDones.get(0).getDestino()));
        String body = emailBody.getContent().concat(construirTabla(emailDones));
        emailBody.setSubject(String.format(emailBody.getSubject(), new Date().toString(), emailDones.get(0).getTraslado(), emailDones.get(0).getOrigen(), emailDones.get(0).getDestino()));
        sendEmailTool(body,emailBody.getEmail(), emailBody.getSubject(), emailBody.getCc(), "COMERSSIA");

    }

    @Override
    public void sendFailedComerssiaEmail(EmailBody emailBody, List<EmailDone> emailDones) throws Exception {
        emailBody.setContent(String.format(emailBody.getContent(), new Date().toString(),emailDones.get(0).getConsecutivo(), emailDones.get(0).getTraslado(), emailDones.get(0).getOrigen(), emailDones.get(0).getDestino()));
        String body = emailBody.getContent().concat(construirTabla(emailDones));  
        emailBody.setSubject(String.format(emailBody.getSubject(), emailDones.get(0).getTraslado(), emailDones.get(0).getOrigen(), emailDones.get(0).getDestino()));

        sendEmailTool(body,emailBody.getEmail(), emailBody.getSubject(), emailBody.getCc(), "COMERSSIA");
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
        String[] cci = cc != null && cc.contains(",") ? cc.split(",") : new String[]{cc == null ? "alejandro.lindarte@assaabloy.com" : cc};
        Email emailEn = new Email(email, textMessage, subject, cc, origen);
        helper.setTo(to);
        helper.setCc(cci);
        helper.setText(textMessage, true);
        helper.setSubject(subject);
        sender.send(message);

        this.emailRepository.save(emailEn);

        LOGGER.info("Mail enviado!");

    }

	@Override
	public Page<Email> listarEmail(String origen, Pageable pageable) throws Exception {
		return this.emailRepository.listarNotificaciones(origen,pageable);
	}

    /**
     * 
     */
    private String construirTabla(List<EmailDone> emailDones){
        
        String html = "<table style=\"height: 344px;\">"+
        "<thead>"+
        "<tr style=\"height: 36px;\">"+
        "<td style=\"height: 36px; width: 84.9375px;\">Consecutivo</td>"+
        "<td style=\"height: 36px; width: 68px;\">Codigo origen</td>"+
        "<td style=\"height: 36px; width: 68px;\">Codigo destino</td>"+
        "<td style=\"height: 36px; width: 68px;\">Cantidad</td>"+
        "<td style=\"height: 36px; width: 68px;\">SKU</td>"+
        "<td style=\"height: 36px; width: 80.75px;\">Descripcion</td>"+
        "<td style=\"height: 36px; width: 68px;\">Status</td>"+
        "</tr>"+
        "</thead>"+
        "<tbody>";

        StringBuilder cuerpotabla = new StringBuilder();

        for(EmailDone emailDone: emailDones)
        {
            String tr = "<tr style=\"height: 54px;\">";
            String consecutivo = "<td style=\"height: 54px; width: 84.9375px;\">%s</td>";        
            String codigoorigen = "<td style=\"height: 54px; width: 84.9375px;\">%s</td>";
            String codigodestino = "<td style=\"height: 54px; width: 84.9375px;\">%s</td>";
            String cantidad = "<td style=\"height: 54px; width: 84.9375px;\">%s</td>";
            String sku = "<td style=\"height: 54px; width: 84.9375px;\">%s</td>";
            String descripcion = "<td style=\"height: 54px; width: 84.9375px;\">%s</td>";
            String status = "<td style=\"height: 54px; width: 84.9375px;\">%s</td>";
            String trclose = "</tr>";

            cuerpotabla.append(tr).append(String.format(consecutivo, emailDone.getConsecutivo()));
            cuerpotabla.append(String.format(codigoorigen, emailDone.getOrigen()));
            cuerpotabla.append(String.format(codigodestino, emailDone.getDestino()));
            cuerpotabla.append(String.format(cantidad, emailDone.getCantidad()));
            cuerpotabla.append(String.format(sku, emailDone.getSku()));
            cuerpotabla.append(String.format(descripcion, emailDone.getDescripcion()));
            cuerpotabla.append(String.format(status, emailDone.getStatus())).append(trclose);

        }

        String htmlclose = "</tbody>"+
        "</table>";
	
        return html.concat(cuerpotabla.toString()).concat(htmlclose);

    }

    /**
     * 
     * @param token
     */
    @Override
    public Object saveToken(String token) throws Exception{

        if(this.tokenRepository.getToken(token) == null){
            Token token_1 =  new Token(token, LocalDateTime.now());
            this.tokenRepository.save(token_1);
            return token;
        }else{
            return "FAIL";
        }

        

    }




}
