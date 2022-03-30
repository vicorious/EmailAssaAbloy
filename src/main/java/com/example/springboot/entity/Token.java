package com.example.springboot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Mails_Token")
public class Token implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mt_Id")
    private Integer token;
    @Lob
    @Column(name = "mt_Token")
    private String t_Token;
    @Column(name = "mt_Fecha")
    private LocalDateTime t_fecha;

    public Token(){
        
    }

    public Token(String t_Token, LocalDateTime t_fecha) {
        this.t_Token = t_Token;
        this.t_fecha = t_fecha;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public String getT_Token() {
        return t_Token;
    }

    public void setT_Token(String t_Token) {
        this.t_Token = t_Token;
    }

    public LocalDateTime getT_fecha() {
        return t_fecha;
    }

    public void setT_fecha(LocalDateTime t_fecha) {
        this.t_fecha = t_fecha;
    }
}