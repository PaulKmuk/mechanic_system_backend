package pl.mechanicsystem.dto.clients;

import pl.mechanicsystem.dto.clients.dto.ClientCarDTO;

import java.time.Instant;
import java.util.List;

public class ClientResponse {

    private Integer clntid;
    private String nam1;
    private String nam2;
    private String phone;
    private String email;
    private Instant insstmp;

    private List<ClientCarDTO> cars;

    public ClientResponse(Integer clntid, String nam1, String nam2, String phone, String email, Instant insstmp, List<ClientCarDTO> cars) {
        this.clntid = clntid;
        this.nam1 = nam1;
        this.nam2 = nam2;
        this.phone = phone;
        this.email = email;
        this.insstmp = insstmp;
        this.cars = cars;
    }

    public Integer getClntid() {
        return clntid;
    }

    public void setClntid(Integer clntid) {
        this.clntid = clntid;
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

    public List<ClientCarDTO> getCars() {
        return cars;
    }

    public void setCars(List<ClientCarDTO> cars) {
        this.cars = cars;
    }
}
