package cl.ey.security;

import java.io.IOException;
import java.util.Map;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;


@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		try {			
			if (jwtTokenUtil.checkJWTToken(request, response)) {
				Claims claims = jwtTokenUtil.validateToken(request);
				if (claims.get("authorities") != null) {
					jwtTokenUtil.setUpSpringAuthentication(claims);
				} else {
					SecurityContextHolder.clearContext();
				}
			}
			chain.doFilter(request, response);

		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		    response.setContentType("application/json");
			ObjectMapper mapper = new ObjectMapper();
		    mapper.writeValue(response.getWriter(), Map.of("error", e.getMessage()));
			return;
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		    response.setContentType("application/json");
		    response.getWriter().write("{\"error\":\"Invalid token\"}");
		    return;
		}
	}

}
