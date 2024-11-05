package echo.authentication.jwt;

import echo.authentication.dto.UserJwtDto;
import echo.authentication.dto.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Getter
@Component
public class JwtProvider {

    private final SecretKey secretKey;

    @Autowired
    public JwtProvider(@Value("${jwt.secret.access}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String generateToken(UserJwtDto userJwtDto) {
        Instant accessExpiration = Instant.now()
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .plus(1, ChronoUnit.DAYS);

        return Jwts.builder()
                .subject(userJwtDto.login())
                .expiration(Date.from(accessExpiration))
                .signWith(secretKey)
                .claim("role", UserRole.USER)
                .compact();
    }
}
