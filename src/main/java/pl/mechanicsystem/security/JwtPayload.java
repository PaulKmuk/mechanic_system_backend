package pl.mechanicsystem.security;

public class JwtPayload {

    private Long iserId;
    private String login;
    private String name;
    private long issuedAt;
    private long expiresAt;

    public JwtPayload(Long iserId, String login, String name, long issuedAt, long expiresAt) {
        this.iserId = iserId;
        this.login = login;
        this.name = name;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public Long getIserId() {
        return iserId;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public long getIssuedAt() {
        return issuedAt;
    }

    public long getExpiresAt() {
        return expiresAt;
    }
}
