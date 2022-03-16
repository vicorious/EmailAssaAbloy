package com.example.springboot.utils;


public class Constantes {
    public static String COMERSSIA_EMAIL_DONE = "Asunto: “ESTADO” Orden de traslado N°:%s Origen: %s    Destino: %s.\n" +
            "\n" +
            "Cuerpo del correo:\n" +
            "Se informa que el día de hoy %s se ha generado un despacho con los siguientes resultados:\n" +
            "número de orden de traslado: %s, desde %s, con destino %s, \n" +
            "con las siguientes características:";

    public static String COMERSSIA_EMAIL_FAILED = "Asunto: “ESTADO” Orden de traslado N°:%s Origen: %s    \n" +
            "Cuerpo del correo:\n" +
            "Se informa que el día de hoy %s se ha generado un ERROR (%s) con orden de traslado: xxxxxxxxxxxxx, desde xxxxxxx, con destino xxxxxxxx.\n" +
            "con las siguientes características:";


    
}