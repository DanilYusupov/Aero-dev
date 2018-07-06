package com.gdc.aerodev.model;

/**
 * {@code Cr} is the company representative. Only checked persons can register as {@code Cr} from his company.
 * Being interested {@code Cr} can make offer to {@code User} to make some {@code Project}. All relationships
 * between {@code Cr} and {@code User} will bind in SQL {@code Offer} descriptor table.
 *
 * @author Yusupov Danil
 */
public class Cr {

    private Long crId;
    /**
     * crName used as <b>nickname</b> to get access to the service
     */
    private String crName;
    private String crPassword;
    private String crEmail;
    private Long companyId;
    private String crFirstName;
    private String crLastName;
    private String crPosition;

    public Cr() {
    }

    public Cr(Long crId, String crName, String crPassword, String crEmail, Long companyId, String crFirstName, String crLastName, String crPosition) {
        this.crId = crId;
        this.crName = crName;
        this.crPassword = crPassword;
        this.crEmail = crEmail;
        this.companyId = companyId;
        this.crFirstName = crFirstName;
        this.crLastName = crLastName;
        this.crPosition = crPosition;
    }

    public Cr(String crName, String crPassword, String crEmail, Long companyId, String crFirstName, String crLastName, String crPosition) {
        this.crName = crName;
        this.crPassword = crPassword;
        this.crEmail = crEmail;
        this.companyId = companyId;
        this.crFirstName = crFirstName;
        this.crLastName = crLastName;
        this.crPosition = crPosition;
    }

    public Long getCrId() {
        return crId;
    }

    public Cr setCrId(Long crId) {
        this.crId = crId;
        return this;
    }

    public String getCrName() {
        return crName;
    }

    public Cr setCrName(String crName) {
        this.crName = crName;
        return this;
    }

    public String getCrPassword() {
        return crPassword;
    }

    public Cr setCrPassword(String crPassword) {
        this.crPassword = crPassword;
        return this;
    }

    public String getCrEmail() {
        return crEmail;
    }

    public Cr setCrEmail(String crEmail) {
        this.crEmail = crEmail;
        return this;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public Cr setCompanyId(Long companyId) {
        this.companyId = companyId;
        return this;
    }

    public String getCrFirstName() {
        return crFirstName;
    }

    public Cr setCrFirstName(String crFirstName) {
        this.crFirstName = crFirstName;
        return this;
    }

    public String getCrLastName() {
        return crLastName;
    }

    public Cr setCrLastName(String crLastName) {
        this.crLastName = crLastName;
        return this;
    }

    public String getCrPosition() {
        return crPosition;
    }

    public Cr setCrPosition(String crPosition) {
        this.crPosition = crPosition;
        return this;
    }
}
