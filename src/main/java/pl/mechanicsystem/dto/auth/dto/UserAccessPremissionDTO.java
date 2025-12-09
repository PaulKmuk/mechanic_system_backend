package pl.mechanicsystem.dto.auth.dto;

import java.util.List;

public class UserAccessPremissionDTO {

    private String resource;
    private String path;
    private List<String> premissions;

    public UserAccessPremissionDTO(String resource, String path, List<String> premissions) {
        this.resource = resource;
        this.path = path;
        this.premissions = premissions;
    }

    public String getResource() {
        return resource;
    }

    public String getPath() {
        return path;
    }

    public List<String> getPremissions() {
        return premissions;
    }
}
