package com.cgemployment.cge_rozgarapp;

public class GramPanchaytMasterModelClass {
    String gramPanchayatName,gramPanchayatLocalCode;

    public GramPanchaytMasterModelClass(String gramPanchayatName, String gramPanchayatLocalCode) {
        this.gramPanchayatName = gramPanchayatName;
        this.gramPanchayatLocalCode = gramPanchayatLocalCode;
    }

    public String getGramPanchayatName() {
        return gramPanchayatName;
    }

    public void setGramPanchayatName(String gramPanchayatName) {
        this.gramPanchayatName = gramPanchayatName;
    }

    public String getGramPanchayatLocalCode() {
        return gramPanchayatLocalCode;
    }

    public void setGramPanchayatLocalCode(String gramPanchayatLocalCode) {
        this.gramPanchayatLocalCode = gramPanchayatLocalCode;
    }
}
