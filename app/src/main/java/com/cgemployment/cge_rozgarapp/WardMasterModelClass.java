package com.cgemployment.cge_rozgarapp;

public class WardMasterModelClass {
    String wardCode, wardName, wardNumber;

    public WardMasterModelClass(String wardCode, String wardName, String wardNumber) {
        this.wardCode = wardCode;
        this.wardName = wardName;
        this.wardNumber = wardNumber;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardNumber() {
        return wardNumber;
    }

    public void setWardNumber(String wardNumber) {
        this.wardNumber = wardNumber;
    }
}
