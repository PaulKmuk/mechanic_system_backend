package pl.mechanicsystem.service;

import org.springframework.stereotype.Service;
import pl.mechanicsystem.service.auth.LoginResult;

@Service
public interface AuthService {

    LoginResult login(String login, String password);
    LoginResult refresh(String refreshToken);
}
