package basic.main.security.jwt;

import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.Date;
import java.util.Map;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PublicKey;

@Service
public class JwtService {

    private final JwtKeyProvider keyProvider;
    private final long accessTokenValidityMs = 900_000; // 15 минут
    private final long refreshTokenValidityMs = 1_209_600_000L; // 14 дней

    public JwtService(JwtKeyProvider keyProvider) {
        this.keyProvider = keyProvider;
    }

    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return generateToken(subject, claims, accessTokenValidityMs);
    }

    public String generateRefreshToken(String subject) {
        return generateToken(subject, null, refreshTokenValidityMs);
    }

    private String generateToken(String subject, Map<String, Object> claims, long validityMs) {
        PrivateKey privateKey = keyProvider.getPrivateKey();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityMs);

        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims != null ? claims : Map.of())
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(privateKey, SignatureAlgorithm.RS256)
                .compact();
    }

    public Claims parseToken(String token) {
        PublicKey publicKey = keyProvider.getPublicKey();
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, String subject) {
        try {
            Claims claims = parseToken(token);
            return claims.getSubject().equals(subject) && claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}