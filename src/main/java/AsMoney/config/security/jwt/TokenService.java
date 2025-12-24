package AsMoney.config.security.jwt;


import AsMoney.config.security.jwt.exception.InvalidTokenException;
import AsMoney.modules.user.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class TokenService {

    @Value("${spring.jwt.SECRET_KEY}")
    private String secretKey;

    public String generateToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        String token = JWT.create()
                .withIssuer("AsMoney")
                .withSubject(user.getId().toString())
                .withClaim("name", user.getName())
                .withExpiresAt(Instant.now().plus(Duration.ofHours(8)))
                .sign(algorithm);

        return token;

    }

    public TokenData validateToken(String token) {

        token = token.replace("Bearer ", "");

        Algorithm algorithm = Algorithm.HMAC256(this.secretKey);

        try {
            DecodedJWT jwt = JWT.require(algorithm).build().verify(token);

            String subject = jwt.getSubject();
            String name = jwt.getClaim("name").toString();

            return new TokenData(subject, name);


        } catch (JWTVerificationException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            throw new InvalidTokenException();
        }
    }

}
