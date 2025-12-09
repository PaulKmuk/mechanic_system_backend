package pl.mechanicsystem.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ms_user")
public class MsUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private Long gid;

    @Column (name = "nam1")
    private String nam1;

    @Column (name = "nam2")
    private String nam2;

    @Column (name = "login")
    private String login;

    @Column (name = "password")
    private String password;

    @Column (name = "status")
    private String status;

    @Column (name = "insusrid")
    private Long insusrid;

    @Column (name = "insstmp")
    private LocalDateTime insstmp;

    @Column (name = "updusrid")
    private Long updusrid;

    @Column (name = "updstmp")
    private LocalDateTime updstmp;

    public MsUser() {
    }

    public MsUser(String password, Long gid, String nam1, String nam2, String login, String status, Long insusrid, LocalDateTime insstmp, Long updusrid, LocalDateTime updstmp) {
        this.password = password;
        this.gid = gid;
        this.nam1 = nam1;
        this.nam2 = nam2;
        this.login = login;
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

    public String getNam1() {
        return nam1;
    }

    public void setNam1(String nam1) {
        this.nam1 = nam1;
    }

    public String getNam2() {
        return nam2;
    }

    public void setNam2(String nam2) {
        this.nam2 = nam2;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public LocalDateTime getInsstmp() {
        return insstmp;
    }

    public void setInsstmp(LocalDateTime insstmp) {
        this.insstmp = insstmp;
    }

    public Long getUpdusrid() {
        return updusrid;
    }

    public void setUpdusrid(Long updusrid) {
        this.updusrid = updusrid;
    }

    public LocalDateTime getUpdstmp() {
        return updstmp;
    }

    public void setUpdstmp(LocalDateTime updstmp) {
        this.updstmp = updstmp;
    }

    @Override
    public String toString() {
        return "MsUser{" +
                "gid=" + gid +
                ", nam1='" + nam1 + '\'' +
                ", nam2='" + nam2 + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", status='" + status + '\'' +
                ", insusrid=" + insusrid +
                ", insstmp=" + insstmp +
                ", updusrid=" + updusrid +
                ", updstmp=" + updstmp +
                '}';
    }
}
