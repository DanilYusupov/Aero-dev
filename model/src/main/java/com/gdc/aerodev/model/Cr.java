package com.gdc.aerodev.model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.List;

/**
 * {@code Cr} is the company representative. Only checked persons can register as {@code Cr} from his company.
 * Being interested {@code Cr} can make offer to {@code User} to make some {@code Project}. All relationships
 * between {@code Cr} and {@code User} will bind in SQL {@code Offer} descriptor table.
 *
 * @author Yusupov Danil
 */
@Entity
@Table(schema = "aero", name = "company_representatives")
public class Cr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cr_id")
    private Long crId;
    /**
     * crName used as <b>nickname</b> to get access to the service
     */
    @Column(name = "cr_name",  nullable = false)
    private String crName;
    @Column(name = "cr_pass",  nullable = false)
    private String crPassword;
    @Column(name = "cr_email", nullable = false)
    private String crEmail;

    @ManyToOne
    @JoinColumn(name = "comp_id", foreignKey = @ForeignKey(name = "comp_fk"))
    @NotNull
    private Company company;

    @Column(name = "cr_first_name", nullable = false)
    private String crFirstName;
    @Column(name = "cr_last_name", nullable = false)
    private String crLastName;
    @Column(name = "cr_position")
    private String crPosition;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "offeredCr", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Offer> offers;

    public Cr() {
    }

    public Cr(String crName, String crPassword, String crEmail, Company company, String crFirstName, String crLastName, String crPosition) {
        this.crName = crName;
        this.crPassword = crPassword;
        this.crEmail = crEmail;
        this.company = company;
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

    public Company getCompany() {
        return company;
    }

    public Cr setCompany(Company company) {
        this.company = company;
        return this;
    }
}
