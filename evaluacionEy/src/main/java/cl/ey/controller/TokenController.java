package cl.ey.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ey.model.JwtResponse;
import cl.ey.model.LoginRequest;
import cl.ey.security.JwtTokenUtil;
import cl.ey.service.UsuarioService;


@CrossOrigin
@RestController
@RequestMapping(value="/token", produces=MediaType.APPLICATION_JSON_VALUE)
public class TokenController {

	private static final Logger logger = LogManager.getLogger(TokenController.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UsuarioService userService;

	@PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?>  getToken(@RequestBody LoginRequest login) {
		logger.debug("Token");

		if (!userService.validUserPass(login)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
	                .body("Usuario o contraseña inválidossss");
	    }
		
		String token = jwtTokenUtil.getJWTToken(login.getEmail());
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping(value="/s", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getToken(@RequestBody String body) {
	    logger.info("BODY RAW: " + body);
	    return ResponseEntity.ok(body);
	}
}
