package ru.cft.shift.intensive.cool_drive.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.cft.shift.intensive.cool_drive.dto.AccountDetails;

import java.util.Calendar;
import java.util.Date;
import java.util.function.Function;

/**
 * Данный компонент предоставляет набор методов для работы с токеном.
 */
@Component
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);
    @Getter
    private String secret;

    @Value("${security.tokenLife}")
    private Long tokenLife; // время жизни токена в минутах

    public JwtUtils() {
        this.secret = "driveAPI";
    }

    /**
     * вынуть из токена имя пользователя
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * вынуть из токена дату истечения
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * вытащить утверждение, записаное в токене
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * вынуть из токена все записанниые утверждения
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * проверить не истекло ли время жизни токена
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * создание токена пользователя
     */
    public String generateToken(AccountDetails userDetails) {
        return Jwts.builder()
                .setSubject((userDetails.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(Calendar.getInstance().getTimeInMillis() + (tokenLife * 60 * 1000)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}


