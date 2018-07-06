package com.gdc.aerodev.model;

public class Company {

    Long compId;
    String compName;
    String compWebsite;
    String compLogoUrl;

    public Company() {
    }

    public Company(Long compId, String compName, String compWebsite, String compLogoUrl) {
        this.compId = compId;
        this.compName = compName;
        this.compWebsite = compWebsite;
        this.compLogoUrl = compLogoUrl;
    }

    public Company(String compName, String compWebsite, String compLogoUrl) {
        this.compName = compName;
        this.compWebsite = compWebsite;
        this.compLogoUrl = compLogoUrl;
    }

    public Long getCompId() {
        return compId;
    }

    public Company setCompId(Long compId) {
        this.compId = compId;
        return this;
    }

    public String getCompName() {
        return compName;
    }

    public Company setCompName(String compName) {
        this.compName = compName;
        return this;
    }

    public String getCompWebsite() {
        return compWebsite;
    }

    public Company setCompWebsite(String compWebsite) {
        this.compWebsite = compWebsite;
        return this;
    }

    public String getCompLogoUrl() {
        return compLogoUrl;
    }

    public Company setCompLogoUrl(String compLogoUrl) {
        this.compLogoUrl = compLogoUrl;
        return this;
    }
}
