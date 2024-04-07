package com.cgemployment.cge_rozgarapp;

public class GramModelClass {

    String villageName,villageLocalBodyCode;

    public GramModelClass(String villageName, String villageLocalBodyCode) {
        this.villageName = villageName;
        this.villageLocalBodyCode = villageLocalBodyCode;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getVillageLocalBodyCode() {
        return villageLocalBodyCode;
    }

    public void setVillageLocalBodyCode(String villageLocalBodyCode) {
        this.villageLocalBodyCode = villageLocalBodyCode;
    }
}
