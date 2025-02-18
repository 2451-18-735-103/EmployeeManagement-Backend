package com.employee.demo.jwt;

import com.employee.demo.exception.CustomSecurityException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtToken {
    @Value("${app.jwt.secret}")
    private String jwtSecret;
    @Value("${app.jwt.expiry}")
    private long jwtExpirationDate;

    @Value("${app.jwt.refreshExpiry}")
    private long jwtRefreshExpirationDate;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String token = Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expireDate)
                .signWith(key()).compact();
        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // get username from Jwt token
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        return username;
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new CustomSecurityException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new CustomSecurityException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new CustomSecurityException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new CustomSecurityException("JWT claims string is empty.");
        } catch (SignatureException ex) {
            throw new CustomSecurityException("JWT signature does not match locally computed signature.");
        }
    }

    //Refresh Token Implementation
    public String generateRefreshToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtRefreshExpirationDate);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token);
    }

}
