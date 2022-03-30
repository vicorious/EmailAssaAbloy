package com.example.springboot.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.*;


import org.json.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.example.springboot.utils.*;

import com.example.springboot.entity.*;

@CrossOrigin(origins = "*",allowedHeaders = "*")
@RestController
@RequestMapping(value = "/notificaciones")
public class EmailRest {

    @Autowired
    private EmailPort emailPort;

    /**
     * Enviar mail desde el smtp de assa abloy
     * @param emailBody
     * @return
     */
    @PostMapping(value = "/enviarEmail")
    @ResponseBody
    public String sendEmail(@RequestBody EmailBody emailBody)  {
        try {
            this.emailPort.sendEmail(emailBody);
        }catch(Exception ex) {
            this.enviarCorreoFallo(emailBody, ex);
            return "FAIL";
        }
        return "OK";
    }

    /**
     * Cambia la plantilla del email para reportar correctamente a comerssia
     * @param emailBody
     * @return
     */
    @PostMapping(value = "/enviarEmailComerssia")
    @ResponseBody
    public String comerssiaNotificaciones(@RequestBody EmailBody emailBody)
    {
        try {
            System.out.println(emailBody.getEmailDoneList());
            this.emailPort.sendComerssiaEmail(
                     emailBody,
                     emailBody.getEmailDoneList());
        }catch(Exception ex) {
            ex.printStackTrace();
            this.enviarCorreoFallo(emailBody, ex);
            return "FAIL";
        }
        return "OK";
    }

    /**
     * Cambia la plantilla del email para reportar errores de comerssia
     * @param emailBody
     * @return
     */
    @PostMapping(value = "/enviarEmailErroresComerssia")
    @ResponseBody
    public String comerssiaFalloNotificaciones(@RequestBody EmailBody emailBody)
    {
        try {
            this.emailPort.sendFailedComerssiaEmail(
                    emailBody,
                    emailBody.getEmailDoneList());
        }catch(Exception ex) {
            this.enviarCorreoFallo(emailBody, ex);
            return "FAIL";
        }
        return "OK";
    }


    /**
     * Retornar todas las notificaciones en la ODS
     * @return
     */
    @GetMapping(value = "/mailsp")
    @ResponseBody
    public ResponseEntity<?> mails(String origen, Pageable pageable)  {
        try {           
            return new ResponseEntity<>(this.emailPort.listarEmail(origen, pageable), HttpStatus.OK); 
        }catch(Exception ex) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/mails")
    @ResponseBody
    public ResponseEntity<?> mails2(String origen, Pageable pageable)  {
        try {           
            return new ResponseEntity<>(this.emailPort.mails(pageable), HttpStatus.OK); 
        }catch(Exception ex) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/generateToken")
    @ResponseBody
    public ResponseEntity<?> generate()  {
        try {           
            Map<String, String> mapa = new HashMap<String,String>() {};
            mapa.put("token", this.generateToken());
            return new ResponseEntity<>(new JSONObject(mapa).toString(), HttpStatus.OK); 
        }catch(Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    private String generateToken() throws Exception{
        JSONObject payload;
        LocalDateTime ldt;

        ldt = LocalDateTime.now().plusDays(90);
        payload = new JSONObject("{\"sub\":\"1234\",\"aud\":[\"admin\"],"
                + "\"exp\":" + ldt.toEpochSecond(ZoneOffset.UTC) + "}");

        
        return this.emailPort.saveToken(new JWebToken(payload).toString()).toString();
    }
        

    /**
     * Envia un correo plano con la informacion de la excepcion
     * @param emailBody
     * @param ex
     */
    private void enviarCorreoFallo(EmailBody emailBody, Exception ex){
        emailBody.setContent(ex.getMessage());
        try {
            this.emailPort.sendEmail(emailBody);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param emailBody
     * @param ex
     */
    private void enviarCorreoComerssiaFallo(EmailBody emailBody,String traslado,
                                            String origen,
                                            String destino,
                                            String fecha, Exception ex){
        emailBody.setContent(ex.getMessage());
        try {
            this.emailPort.sendFailedComerssiaEmail(emailBody, emailBody.getEmailDoneList());
        } catch (Exception e) {
            this.enviarCorreoFallo(emailBody, ex);
        }
    }

}
