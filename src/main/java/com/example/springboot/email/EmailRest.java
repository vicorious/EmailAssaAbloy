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
    public String SendEmail(@RequestBody EmailBody emailBody)  {
        try {
            this.emailPort.sendEmail(emailBody);
        }catch(Exception ex)
        {
            emailBody.setContent(ex.getMessage());
            this.emailPort.sendComerssiaEmail(emailBody);
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
