package com.cgemployment.cge_rozgarapp;

public class AlertStatusModel {
    String departmentName,jobDescription,websiteUrl;

    public AlertStatusModel(String departmentName, String jobDescription, String websiteUrl) {
        this.departmentName = departmentName;
        this.jobDescription = jobDescription;
        this.websiteUrl = websiteUrl;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
