package cl.ey.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration {

	@Autowired
	JWTAuthorizationFilter jWTAuthorizationFilter;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

	    httpSecurity
	        .csrf(csrf -> csrf
	            .ignoringRequestMatchers(PathRequest.toH2Console())
	            .disable()
	        )
	        .headers(headers -> headers
	            .frameOptions(frame -> frame.disable())
	        )
	        .authorizeHttpRequests(auth -> auth
	        	    .requestMatchers(PathRequest.toH2Console()).permitAll()
	        	    .requestMatchers(
	        	        "/token",
	        	        "/token/",
	        	        "/usuario/ping1"
	        	    ).permitAll()
	        	    .anyRequest().authenticated()
        	)
	        .sessionManagement(session -> session
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        );

	    httpSecurity.addFilterBefore(jWTAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

	    return httpSecurity.build();
	}
}
