package com.example.springboot.email;

public class EmailBody {

        private String email;
        private String cc;
        private String content;
        private String subject;
        private String origen;

    public EmailBody(String email, String cc, String content, String subject, String origen) {
        this.email = email;
        this.cc = cc;
        this.content = content;
        this.subject = subject;
        this.origen = origen;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
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
        @Override
        public String toString() {
            return "EmailBody [email=" + email + ", content=" + content + ", subject=" + subject + "]";
        }


    }


