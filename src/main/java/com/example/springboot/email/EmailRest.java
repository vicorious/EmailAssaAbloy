package com.example.springboot.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping(value = "/comerssia")
public class EmailRest {

    @Autowired
    private EmailPort emailPort;

    /**
     * 
     * @param emailBody
     * @return
     */
    @PostMapping(value = "/notificaciones")
    @ResponseBody
    public String sendEmail(@RequestBody EmailBody emailBody)  {
        try {
            this.emailPort.sendEmail(emailBody);
        }catch(Exception ex)
        {
            emailBody.setContent(ex.getMessage());
            try {
                this.emailPort.sendEmail(emailBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "FAIL";
        }
        return "OK";
    }

    /**
     * 
     * @param emailBody
     * @return
     */
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

    /**
     * 
     * @return
     */
    @GetMapping(value = "/mails")
    public ResponseEntity<?> ListarEmails(@RequestParam String origen,Pageable pageable){
        try {        	
        	String from = "%"+origen+"%";        	
            return new ResponseEntity<>(this.emailPort.listarEmail(from,pageable), HttpStatus.OK);
        } catch (Exception error){
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

}
