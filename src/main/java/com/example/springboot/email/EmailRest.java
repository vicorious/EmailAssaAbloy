package com.example.springboot.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public String comerssiaNotificaciones(@RequestBody EmailBody emailBody,
                                          String traslado,
                                          String origen,
                                          String destino,
                                          String fecha,
                                          String numOrder)
    {
        try {
            this.emailPort.sendComerssiaEmail(
                     emailBody,
                     traslado,
                     origen,
                     destino,
                     fecha,
                     numOrder);
        }catch(Exception ex) {
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
    public String comerssiaFalloNotificaciones(@RequestBody EmailBody emailBody,
                                          String traslado,
                                          String origen,
                                          String destino,
                                          String fecha)
    {
        try {
            this.emailPort.sendFailedComerssiaEmail(
                    emailBody,
                    traslado,
                    origen,
                    fecha,
                    destino);
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
    @PostMapping(value = "/notificaciones")
    @ResponseBody
    public String mails()  {
        try {
            this.emailPort.mails();
        }catch(Exception ex) {
            return "FAIL";
        }
        return "OK";
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
            this.emailPort.sendFailedComerssiaEmail(emailBody, traslado, origen, fecha, destino);
        } catch (Exception e) {
            this.enviarCorreoFallo(emailBody, ex);
        }
    }

}
