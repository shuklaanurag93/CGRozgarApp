package com.cgemployment.cge_rozgarapp;

public class DisabilityspModelClass {

    String code, handicapType;

    public DisabilityspModelClass(String code, String handicapType) {
        this.code = code;
        this.handicapType = handicapType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHandicapType() {
        return handicapType;
    }

    public void setHandicapType(String handicapType) {
        this.handicapType = handicapType;
    }
}
