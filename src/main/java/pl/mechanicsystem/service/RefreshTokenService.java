package pl.mechanicsystem.service;

import org.springframework.stereotype.Service;
import pl.mechanicsystem.entity.MsRefreshtoken;

@Service
public interface RefreshTokenService {

    String createRefreshTokenForUser(Long userid);
    MsRefreshtoken validateRefreshToken(String token);
    void revokeToken(String token);
}
