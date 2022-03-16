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
            emailPort.sendEmail(emailBody);
        }catch(Exception ex)
        {
            emailBody.setContent(ex.getMessage());
            this.emailPort.sendComerssiaEmail(emailBody);
        }
        return "OK";
    }

}
