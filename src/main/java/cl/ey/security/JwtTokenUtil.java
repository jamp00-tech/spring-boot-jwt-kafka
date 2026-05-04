package cl.ey.security;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String HEADER = "Authorization";
    private static final String PREFIX = "Bearer ";
    private static final String SECRET = "87eefb44-32fc-49cd-a4c8-0fc164eacf8187eefb44-32fc-49cd-a4c8-0fc164eacf81";

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public Claims validateToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);

        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
            throw new IllegalArgumentException("JWT token missing or invalid");
        }

        String jwtToken = authenticationHeader.replace(PREFIX, "");

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    public void setUpSpringAuthentication(Claims claims) {
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) claims.get("authorities");

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        authorities.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList())
                );

        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public boolean checkJWTToken(HttpServletRequest request, HttpServletResponse response) {
        String authenticationHeader = request.getHeader(HEADER);
        return authenticationHeader != null && authenticationHeader.startsWith(PREFIX);
    }

    public String getJWTToken(String username) {
        List<GrantedAuthority> grantedAuthorities =
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts.builder()
                .setId("jampJWT")
                .setSubject(username)
                .claim(
                        "authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList())
                )
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(getSigningKey())
                .compact();

        return PREFIX + token;
    }
}