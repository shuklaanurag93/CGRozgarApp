package com.cgemployment.cge_rozgarapp;

public class SubjectModelClass {
    String code,subject;

    public SubjectModelClass(String code, String subject) {
        this.code = code;
        this.subject = subject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
