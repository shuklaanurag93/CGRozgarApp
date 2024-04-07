package com.cgemployment.cge_rozgarapp;

public class HighestQualificationModelClass {

    String code,qualification;

    public HighestQualificationModelClass(String code, String qualification) {
        this.code = code;
        this.qualification = qualification;
    }

    public String getCode() {
        return code;
    }

    public String getQualification() {
        return qualification;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
