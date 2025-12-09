package pl.mechanicsystem.service;

import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {

    String createRefreshTokenForUser(Long userid);

}
