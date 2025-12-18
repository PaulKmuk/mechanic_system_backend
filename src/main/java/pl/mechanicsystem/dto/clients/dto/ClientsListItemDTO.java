package pl.mechanicsystem.dto.clients.dto;

import java.time.Instant;

public class ClientsListItemDTO {

    private Integer clntid;
    private String clnam1;
    private String clnam2;
    private String phone;
    private String email;
    private Instant insstmp;

    public ClientsListItemDTO(Integer clntid, String clnam1, String clnam2, String phone, String email, Instant insstmp) {
        this.clntid = clntid;
        this.clnam1 = clnam1;
        this.clnam2 = clnam2;
        this.phone = phone;
        this.email = email;
        this.insstmp = insstmp;
    }

    public Integer getClntid() {
        return clntid;
    }

    public void setClntid(Integer clntid) {
        this.clntid = clntid;
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

    public Instant getInsstmp() {
        return insstmp;
    }

    public void setInsstmp(Instant insstmp) {
        this.insstmp = insstmp;
    }
}
