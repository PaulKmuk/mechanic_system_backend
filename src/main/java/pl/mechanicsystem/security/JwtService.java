package pl.mechanicsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {

    // Klucz do podpisywania tokenów
    private static final String SECRET = "SECRET_KEY_951357852963741";

    // Czas życia acces_token 10 min > 600 sek
    private static final long ACCESS_TOKEN_EXP_SECONDS = 600L;

    private static final String HMAC_ALGO = "HmacSHA256";

    private final ObjectMapper objectMapper = new ObjectMapper();

    // =====================================
    // GENEROWANIE ACCESS TOKENA
    // =====================================

    public String generateAccessToken(Long userId, String login, String name) {
        try {
            // 1. HEADRE
            Map<String, Object> header = new HashMap<>();
            header.put("alg", "HS256");
            header.put("typ", "JWT");

            String headerJson = objectMapper.writeValueAsString(header);
            String headerPart = base64UrlEncode(headerJson.getBytes((StandardCharsets.UTF_8)));

            // 2. PAYLOAD
            long now = Instant.now().getEpochSecond();
            long exp = now + ACCESS_TOKEN_EXP_SECONDS;

            Map<String, Object> payload = new HashMap<>();
            payload.put("sub", userId);
            payload.put("login", login);
            payload.put("name", name);
            payload.put("iat", now);
            payload.put("exp", exp);

            String payloadJson = objectMapper.writeValueAsString(payload);
            String payloadPart = base64UrlEncode(payloadJson.getBytes(StandardCharsets.UTF_8));

            // 3. PODPIS
            String dataToSign = headerPart + "." + payloadPart;
            String signaturePart = sign(dataToSign);

            // 4. KONCOWY TOKEN
            return headerPart + "." + payloadPart + "." + signaturePart;

        } catch (Exception e) {
            throw new RuntimeException("Error creating JWT", e);
        }
    }

    // =====================================
    // WALIDACJA I PARSOWANIE ACCESS TOKENA
    // =====================================
    public JwtPayload parseAndValidate(String token) {
        try {
            // 1. Rozbijamy token: HEADER, PAYLOAD, SIGNATURE
            String[] parts = token.split("\\.");
            if (parts.length != 3) {
                throw new RuntimeException("Invalid token format");
            }

            String headerPart = parts[0];
            String payloadPart = parts[1];
            String signaturePart = parts[2];

            // 2. Weryfikacja podpisu
            String dataToSign = headerPart + "." + payloadPart;
            String expectedSignature = sign(dataToSign);

            if (!constantTimeEquals(signaturePart, expectedSignature)) {
                throw new RuntimeException("Invalid token signature");
            }

            // 3. Odczyt payloadu (JSON)
            String payloadJson = new String(base64UrlDecode(payloadPart), StandardCharsets.UTF_8);
            @SuppressWarnings("unchecked")
            Map<String, Object> payloadMap = objectMapper.readValue(payloadJson, Map.class);

            Long userId = (Long) payloadMap.get("sub");
            String login = (String) payloadMap.get("login");
            String name = (String) payloadMap.get("name");
            Number iatNum = (Number) payloadMap.get("iat");
            Number expNum = (Number) payloadMap.get("exp");

            long iat = iatNum.longValue();
            long exp = expNum.longValue();
            long now = Instant.now().getEpochSecond();

            // 4. Sprawdzenie czy token nie wygasł
            if(now > exp) {
                throw new RuntimeException("Token expired!");
            }

            return new JwtPayload(userId, login, name, iat, exp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // =====================================
    // METODY POMOCNICZE
    // =====================================

    private String base64UrlEncode(byte[] bytes) {
        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    private byte[] base64UrlDecode(String str) {
        return Base64.getUrlDecoder().decode(str);
    }


    private String sign(String data) throws Exception {
        Mac mac = Mac.getInstance(HMAC_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(SECRET.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
        mac.init(keySpec);
        byte[] signatureBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return base64UrlEncode(signatureBytes);
    }

    // Porównanie w stałym czasie (żeby utrudnić ataki typu timin attack)
    public boolean constantTimeEquals(String a, String b) {
        if(a.length() != b.length()) return false;

        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
            System.out.println(a.charAt(i));
        }
        return result == 0;
    }
}
