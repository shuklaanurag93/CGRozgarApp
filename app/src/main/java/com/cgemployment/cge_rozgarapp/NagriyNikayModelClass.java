package com.cgemployment.cge_rozgarapp;

public class NagriyNikayModelClass {
    String nagarPanchayatCode,nagarPanchayatName;

    public NagriyNikayModelClass(String nagarPanchayatCode, String nagarPanchayatName) {
        this.nagarPanchayatCode = nagarPanchayatCode;
        this.nagarPanchayatName = nagarPanchayatName;
    }

    public String getNagarPanchayatCode() {
        return nagarPanchayatCode;
    }

    public void setNagarPanchayatCode(String nagarPanchayatCode) {
        this.nagarPanchayatCode = nagarPanchayatCode;
    }

    public String getNagarPanchayatName() {
        return nagarPanchayatName;
    }

    public void setNagarPanchayatName(String nagarPanchayatName) {
        this.nagarPanchayatName = nagarPanchayatName;
    }
}
