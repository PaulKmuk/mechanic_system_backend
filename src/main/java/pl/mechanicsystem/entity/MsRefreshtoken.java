package pl.mechanicsystem.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "ms_refreshtoken")
public class MsRefreshtoken {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "gid")
    private Long gid;

    @Column(name = "usrid")
    private Long usrid;

    @Column(name = "token")
    private String token;

    @Column(name = "expstmp")
    private Instant expstmp;

    @Column(name = "status")
    private String status;

    @Column (name = "insusrid")
    private Long insusrid;

    @Column (name = "insstmp")
    private Instant insstmp;

    @Column (name = "updusrid")
    private Long updusrid;

    @Column (name = "updstmp")
    private Instant updstmp;

    public MsRefreshtoken() {
    }

    public MsRefreshtoken(Long gid, Long usrid, String token, Instant expstmp, String status, Long insusrid, Instant insstmp, Long updusrid, Instant updstmp) {
        this.gid = gid;
        this.usrid = usrid;
        this.token = token;
        this.expstmp = expstmp;
        this.status = status;
        this.insusrid = insusrid;
        this.insstmp = insstmp;
        this.updusrid = updusrid;
        this.updstmp = updstmp;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getUsrid() {
        return usrid;
    }

    public void setUsrid(Long usrid) {
        this.usrid = usrid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpstmp() {
        return expstmp;
    }

    public void setExpstmp(Instant expstmp) {
        this.expstmp = expstmp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getInsusrid() {
        return insusrid;
    }

    public void setInsusrid(Long insusrid) {
        this.insusrid = insusrid;
    }

    public Instant getInsstmp() {
        return insstmp;
    }

    public void setInsstmp(Instant insstmp) {
        this.insstmp = insstmp;
    }

    public Long getUpdusrid() {
        return updusrid;
    }

    public void setUpdusrid(Long updusrid) {
        this.updusrid = updusrid;
    }

    public Instant getUpdstmp() {
        return updstmp;
    }

    public void setUpdstmp(Instant updstmp) {
        this.updstmp = updstmp;
    }
}
