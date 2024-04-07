package com.cgemployment.cge_rozgarapp;

public class BlockMasterModelClass {

    String blockCode,blockName;

    public BlockMasterModelClass(String blockCode, String blockName) {
        this.blockCode = blockCode;
        this.blockName = blockName;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }
}
