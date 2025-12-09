package pl.mechanicsystem.dto.auth.dto;

import java.util.List;

public class UserDTO {

    private Long usrid;
    private String login;
    private String nam1;
    private String nam2;
    private List<UserAccessPremissionDTO> access;

    public UserDTO(Long usrid, String login, String nam1, String nam2, List<UserAccessPremissionDTO> access) {
        this.usrid = usrid;
        this.login = login;
        this.nam1 = nam1;
        this.nam2 = nam2;
        this.access = access;
    }

    public Long getUsrid() {
        return usrid;
    }

    public String getLogin() {
        return login;
    }

    public String getNam1() {
        return nam1;
    }

    public String getNam2() {
        return nam2;
    }

    public List<UserAccessPremissionDTO> getAccess() {
        return access;
    }
}
