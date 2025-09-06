// package com.example.auth_service.infrastructure.security;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.example.auth_service.application.ports.TokenService;
// import com.example.auth_service.domain.user.User;
// import com.example.auth_service.infrastructure.config.JwtProperties;
// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Component;

// import java.nio.charset.StandardCharsets;
// import java.time.Instant;
// import java.util.Date;

// /**
//  * Service responsible for issuing JWT tokens for authenticated users.
//  * Implements the TokenService interface.
//  */
// @Component
// @RequiredArgsConstructor
// public class JwtTokenService implements TokenService {

//     /**
//      * JWT configuration properties.
//      */
//     private final JwtProperties props;

//     /**
//      * Issues a JWT access token for the given user.
//      * The token includes claims for email, role, and level.
//      *
//      * @param user the user for whom the token is issued
//      * @return a TokenPair containing the access token and its TTL
//      * @throws IllegalArgumentException if the JWT secret is not configured
//      */
//     @Override
//     public TokenPair issue(User user) {
//         if  (props.getSecret() == null || props.getSecret().isEmpty()) {
//             throw new IllegalArgumentException("secret is required (jwt.secret)");
//         }

//         Instant now = Instant.now();
//         Algorithm algorithm = Algorithm.HMAC256(props.getSecret().getBytes(StandardCharsets.UTF_8));

//         Instant acessExp = now.plusSeconds(props.getAccessTtlSeconds());
//         String acessToken = JWT.create()
//                 .withIssuer(props.getIssuer())
//                 .withAudience(props.getAudience())
//                 .withSubject(user.getId().toString())
//                 .withIssuedAt(Date.from(now))
//                 .withExpiresAt(Date.from(acessExp))
//                 .withClaim("type", "access")
//                 .withClaim("email", user.getEmail().getValue())
//                 .withClaim("role", user.getRole().getValue().name())
//                 .withClaim("level", user.getRole().getValue().getLevel())
//                 .sign(algorithm);

//         return new TokenPair(acessToken, "", (int) props.getAccessTtlSeconds());
//     }
// }
