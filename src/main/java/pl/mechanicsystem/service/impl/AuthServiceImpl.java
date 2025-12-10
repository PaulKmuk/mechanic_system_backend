package pl.mechanicsystem.service.impl;

import org.springframework.stereotype.Service;
import pl.mechanicsystem.dto.auth.dto.UserAccessPremissionDTO;
import pl.mechanicsystem.dto.auth.dto.UserDTO;
import pl.mechanicsystem.entity.MsRefreshtoken;
import pl.mechanicsystem.entity.MsUser;
import pl.mechanicsystem.exception.UserNotFoundException;
import pl.mechanicsystem.repository.MsUserRepository;
import pl.mechanicsystem.security.JwtService;
import pl.mechanicsystem.security.PasswordHasher;
import pl.mechanicsystem.service.AuthService;
import pl.mechanicsystem.service.RefreshTokenService;
import pl.mechanicsystem.service.auth.LoginResult;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordHasher hasher = new PasswordHasher();
    private MsUserRepository msUserRepository;
    private final JwtService jwtService = new JwtService();
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(MsUserRepository msUserRepository, RefreshTokenService refreshTokenService) {
        this.msUserRepository = msUserRepository;
        this.refreshTokenService = refreshTokenService;
    }


    // LOGOWANIE
    @Override
    public LoginResult login(String login, String password) {

        // Wyszukanie użytkownika po "login"
        Optional<MsUser> msUser = msUserRepository.findByLogin(login);
        if (msUser.isEmpty()) {
            throw new UserNotFoundException(
                    "Nie znaleziono użytkonika o loginie: " + login,
                    "USER_NOT_FOUND",
                    "User not found");
        }

        // Sprawdzenie hasła użytkownika
        if(!hasher.checkPassword(password, msUser.get().getPassword())){
            throw new UserNotFoundException(
                    "Podane hasło jest nieprawidłowe",
                    "USER_NOT_FOUND",
                    "User not found"
            );
        }

        // Pobranie listy prawa do funkcji użytkownika
        List<UserAccessPremissionDTO> accessList = loadUserAccess(msUser.get().getGid());

        UserDTO userDTO = new UserDTO(
            msUser.get().getGid(),
            msUser.get().getLogin(),
            msUser.get().getNam1(),
            msUser.get().getNam2(),
            accessList
        );

        // Generowanie access_Token
        String accessToken = jwtService.generateAccessToken(
                msUser.get().getGid(),
                msUser.get().getLogin(),
                msUser.get().getNam1()
        );

        // Generowanie refresh_Token
        String refreshToken = refreshTokenService.createRefreshTokenForUser(msUser.get().getGid());

        return new LoginResult(
                accessToken,
                refreshToken,
                userDTO
        );
    }

    @Override
    public LoginResult refresh(String refreshToken) {
        // Sprawdzamy czy refresh token aktywny i nie wygasł
        MsRefreshtoken tokenEntity = refreshTokenService.validateRefreshToken(refreshToken);
        // Pobieramy userid z refresz token
        Long userId = tokenEntity.getUsrid();
        // Pobieramy użytkownika
        Optional<MsUser> msUser = msUserRepository.findById(userId);
        if (msUser.isEmpty()) {
            throw new UserNotFoundException(
                    "Nie znaleziono użytkownika o ID: " + userId,
                    "USER_NOT_FOUND",
                    "User not found for valid refresh token"
            );
        }
        // Pobranie uprawnień (tak jak w login)
        List<UserAccessPremissionDTO> accessList = loadUserAccess(msUser.get().getGid());
        UserDTO userDTO = new UserDTO(
            msUser.get().getGid(),
            msUser.get().getLogin(),
            msUser.get().getNam1(),
            msUser.get().getNam2(),
            accessList
        );
        // Generujemy nowy access_token
        String accessToken = jwtService.generateAccessToken(
                msUser.get().getGid(),
                msUser.get().getLogin(),
                msUser.get().getNam1()
        );
        // Generujemy nowy refresh_token
        String newRefreshToken = refreshTokenService.createRefreshTokenForUser(msUser.get().getGid());

        return new LoginResult(
                accessToken,
                newRefreshToken,
                userDTO
        );
    }

    // POBRANIE PRAWA DO FUNKCJI UZYTKOWNIKA PO INSUSRID
    private List<UserAccessPremissionDTO> loadUserAccess (Long userId) {
        // Pobranie prawa do funkcji na podstawie przypisanej grup
        // row = 'CLIENTS'  '/clients', 'VIEW;EDIT;DELETE;CREATE'
        List<Object[]> rows = msUserRepository.findUserAccess(userId);

        //  Mapujemy:  code -> path  (CKIENTS -> /clients)
        Map<String, String> pathBycode = new HashMap<>();

        //  Mapujemy: lista uprawnień (z duplikatami)
        Map<String, List<String>> permsByCode = new HashMap<>();

        for (Object[] row : rows) {
            String code = (String) row[0];  // np. 'CLIENTS'
            String path = (String) row[1];  // np. '/clients'
            String access = (String) row[2];  // np. 'VIEW;EDIT;DELETE;CREATE'

            // zapisz code i path (jeśli nie ma)
            pathBycode.putIfAbsent(code, path);

            // druga lista code -> lista uprawnień
            permsByCode.putIfAbsent(code, new ArrayList<>());
            List<String> permList = permsByCode.get(code);

            if (access != null && !access.isBlank()){
                String[] parts = access.split(";");
                for(String p : parts) {
                    String perm = p.trim();
                    if (perm.isEmpty()) continue;
                    permList.add(perm);
                }
            }
        }

        // Budajemy finalną listę DTO, bez duplikatów
        List<UserAccessPremissionDTO> accessList = new ArrayList<>();
        for (String code : permsByCode.keySet()) {
            String path = pathBycode.getOrDefault(code, "");

            List<String> permList = permsByCode.get(code);

            List<String> uniquePerms = new ArrayList<>(new LinkedHashSet<>(permList));

            accessList.add(new UserAccessPremissionDTO(
                    code,
                    path,
                    uniquePerms
            ));
        }
        return accessList;
    }
}
