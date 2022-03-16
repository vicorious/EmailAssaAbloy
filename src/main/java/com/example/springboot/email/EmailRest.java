package com.example.springboot.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/comerssia")
public class EmailRest {

    @Autowired
    private EmailPort emailPort;

    @PostMapping(value = "/notificaciones")
    @ResponseBody
    public String sendEmail(@RequestBody EmailBody emailBody)  {
        try {
            this.emailPort.sendEmail(emailBody);
        }catch(Exception ex)
        {
            emailBody.setContent(ex.getMessage());
            try {
                this.emailPort.sendComerssiaEmail(emailBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "FAIL";
        }
        return "OK";
    }

    @PostMapping(value = "/comerssianotificaciones")
    @ResponseBody
    public String comerssiaNotificaciones(@RequestBody EmailBody emailBody)  {
        try {
            this.emailPort.sendComerssiaEmail(emailBody);
        }catch(Exception ex)
        {
            emailBody.setContent(ex.getMessage());
            try {
                this.emailPort.sendFailedComerssiaEmail(emailBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "FAIL";
        }
        return "OK";
    }

    @PostMapping(value = "/mails")
    @ResponseBody
    public String mails()  {
        try {
            this.emailPort.mails();
        }catch(Exception ex)
        {
            ex.printStackTrace();
            return "FAIL";
        }
        return "OK";
    }

}
