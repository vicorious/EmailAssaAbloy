package com.example.springboot.entity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import java.io.Serializable;
@Entity(name="Notificaciones")
public class Email implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "n_Id")
    private Integer id;
    @Column(name = "n_Destinatario")
    private String email;
    @Column(name = "n_Contenido")
    private String content;
    @Column(name = "n_Asunto")
    private String subject;
    @Column(name = "n_Copia")
    private String cc;
    @Column(name = "n_Origen")
    private String from;

    public Email() {
    	
    }
    
    public Email(String email, String content, String subject, String cc, String from) {
        this.email = email;
        this.content = content;
        this.subject = subject;
        this.cc = cc;
        this.from = from;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
    
}
