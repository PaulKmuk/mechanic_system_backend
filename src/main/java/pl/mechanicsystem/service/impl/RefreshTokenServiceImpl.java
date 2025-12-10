package pl.mechanicsystem.service.impl;

import org.springframework.stereotype.Service;
import pl.mechanicsystem.entity.MsRefreshtoken;
import pl.mechanicsystem.exception.RefreshTokenExpiredException;
import pl.mechanicsystem.exception.RefreshTokenNotFoundException;
import pl.mechanicsystem.repository.MsRefreshtokenRepository;
import pl.mechanicsystem.service.RefreshTokenService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final long REFRESH_TOKEN_EXP_MINUTES = 60L;

    private final MsRefreshtokenRepository msRefreshtokenRepository;

    public RefreshTokenServiceImpl(MsRefreshtokenRepository msRefreshtokenRepository) {
        this.msRefreshtokenRepository = msRefreshtokenRepository;
    }

    @Override
    public String createRefreshTokenForUser(Long userid) {

        Instant now = Instant.now();

        // 1. Oznacz wszystkie istniejące aktywne tokeny jako "R" Revoked
        List<MsRefreshtoken> activeTokens =
                msRefreshtokenRepository.findByUsridAndStatus(userid, "A");

        for (MsRefreshtoken t : activeTokens) {
            t.setStatus("R");
            t.setUpdusrid(userid);
            t.setUpdstmp(now);
        }

        // UPDATE - deazaktywacja istniejących tokenów
        if (!activeTokens.isEmpty()) {
            msRefreshtokenRepository.saveAll(activeTokens);
        }

        // 2. Generujemy nowy losowy refres token
        String token = UUID.randomUUID().toString();

        Instant expirestAt = now.plus(REFRESH_TOKEN_EXP_MINUTES, ChronoUnit.MINUTES);

        MsRefreshtoken newToken = new MsRefreshtoken();
        newToken.setUsrid(userid);
        newToken.setToken(token);
        newToken.setExpstmp(expirestAt);
        newToken.setStatus("A");
        newToken.setInsusrid(userid);
        newToken.setInsstmp(now);
        newToken.setUpdusrid(userid);
        newToken.setUpdstmp(now);

        // INSERT - nowy token
        msRefreshtokenRepository.save(newToken);

        return token;
    }

    @Override
    public MsRefreshtoken validateRefreshToken(String token) {
        // Sprawdzamy podany refresh_token
        Optional<MsRefreshtoken> msRefreshtoken = msRefreshtokenRepository
                .findByTokenAndStatus(token, "A");
        if(msRefreshtoken.isEmpty()) {
            throw new RefreshTokenNotFoundException(
                    "Nieprawidłowy refresh_token",
                    "REFRESH_TOKEN_NOT_FOUND",
                    "Token nie istnieje lub status != 'A'"
            );
        }
        Instant now = Instant.now();

        // Sprawdzamy czy token jest ważny
        if(msRefreshtoken.get().getExpstmp().isBefore(now)) {
            // Jeśłi token nie ważny - zmiana statusu i wyjątek
            msRefreshtoken.get().setStatus("R");
            msRefreshtoken.get().setUpdstmp(now);
            msRefreshtokenRepository.save(msRefreshtoken.get());
            throw new RefreshTokenExpiredException(
                    "Refresh token wygasł",
                    "REFRESH_TOKEN_EXPIRED",
                    "Token wygasł o " + msRefreshtoken.get().getExpstmp()
            );
        }
        return msRefreshtoken.get();
    }

    @Override
    public void revokeToken(String token) {
        Optional<MsRefreshtoken> msRefreshtoken = msRefreshtokenRepository
                .findByTokenAndStatus(token, "A");
        if(msRefreshtoken.isEmpty()) {
            throw new RefreshTokenNotFoundException(
                    "Nieprawidłowy refresh_token",
                    "REFRESH_TOKEN_NOT_FOUND",
                    "Token nie istnieje lub status != 'A'"
            );
        }
        msRefreshtoken.get().setStatus("R");
        msRefreshtoken.get().setUpdstmp(Instant.now());
        msRefreshtokenRepository.save(msRefreshtoken.get());
    }
}
