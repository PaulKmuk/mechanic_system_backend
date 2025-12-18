package pl.mechanicsystem.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "ms_client")
public class MsClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private Integer gid;

    @Column(name = "clnam1")
    private String clnam1;

    @Column(name = "clnam2")
    private String clnam2;

    @Column(name = "status")
    private String status;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "insusrid")
    private int insusrid;

    @Column(name = "insstmp")
    private Instant insstmp;

    @Column(name = "updusrid")
    private int updusrid;

    @Column(name = "updstmp")
    private Instant updstmp;

    public MsClient() {
    }

    public MsClient(Integer gid, String clnam1, String clnam2, String status, String phone, String email, int insusrid, Instant insstmp, int updusrid, Instant updstmp) {
        this.gid = gid;
        this.clnam1 = clnam1;
        this.clnam2 = clnam2;
        this.status = status;
        this.phone = phone;
        this.email = email;
        this.insusrid = insusrid;
        this.insstmp = insstmp;
        this.updusrid = updusrid;
        this.updstmp = updstmp;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getClnam1() {
        return clnam1;
    }

    public void setClnam1(String clnam1) {
        this.clnam1 = clnam1;
    }

    public String getClnam2() {
        return clnam2;
    }

    public void setClnam2(String clnam2) {
        this.clnam2 = clnam2;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getInsusrid() {
        return insusrid;
    }

    public void setInsusrid(int insusrid) {
        this.insusrid = insusrid;
    }

    public Instant getInsstmp() {
        return insstmp;
    }

    public void setInsstmp(Instant insstmp) {
        this.insstmp = insstmp;
    }

    public int getUpdusrid() {
        return updusrid;
    }

    public void setUpdusrid(int updusrid) {
        this.updusrid = updusrid;
    }

    public Instant getUpdstmp() {
        return updstmp;
    }

    public void setUpdstmp(Instant updstmp) {
        this.updstmp = updstmp;
    }
}
