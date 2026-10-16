package org.example.SistemaInventarioAPI.security;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.jwk.source.ImmutableSecret;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.stream.Collectors;



@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String jwtSecret;

    private SecretKey secretKey;
    private JwtEncoder jwtEncoder;

    public JwtService() {
    }
    @PostConstruct
    public void init() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);

        secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
        jwtEncoder = new NimbusJwtEncoder(
                new ImmutableSecret<SecurityContext>(secretKey)
        );
    }
    public String generarToken(UserDetails userDetails) {

        Instant ahora = Instant.now();

        String roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(userDetails.getUsername())
                .claim("scope", roles)
                .issuedAt(ahora)
                .expiresAt(ahora.plus(30, ChronoUnit.MINUTES))
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

        return jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims)
        ).getTokenValue();
    }

}
