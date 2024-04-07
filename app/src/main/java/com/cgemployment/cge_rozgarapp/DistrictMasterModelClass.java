package com.cgemployment.cge_rozgarapp;

public class DistrictMasterModelClass {

    String districtCode,districtName;

    public DistrictMasterModelClass(String districtCode, String districtName) {
        this.districtCode = districtCode;
        this.districtName = districtName;
    }

    public String getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }
}
