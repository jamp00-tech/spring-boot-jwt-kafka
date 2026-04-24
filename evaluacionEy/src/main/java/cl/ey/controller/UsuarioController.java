package cl.ey.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.ey.implement.UsuarioImplement;
import cl.ey.model.Usuarios;
import cl.ey.response.ResponseHandler;

@RestController
@RequestMapping(value="/usuario", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

	private static final Logger logger = LogManager.getLogger(UsuarioController.class);
	
	private final String PREFIX = "Bearer ";
	private final String HEADER = "Authorization";

	@Autowired
	private UsuarioImplement usuarioImplement;

	@GetMapping("/ping1")
	public ResponseEntity<Object> ping2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  String text = "Ping uno";
		  return ResponseHandler.generateResponse(text, HttpStatus.OK);
	}
	
	// Crea registro Usuario
	@PostMapping("/registra")
	public ResponseEntity<Object> registroUsuario(HttpServletRequest request, @RequestBody @Valid Usuarios usuario ) {
		if (usuarioImplement.existEmail(usuario.getEmail())) {
			return ResponseHandler.generateResponse("Correo ya registrado", HttpStatus.CONFLICT);
		}

		String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
		usuario.setToken(jwtToken);
		logger.info(usuario.toString());
		return new ResponseEntity<Object>(usuarioImplement.registraUsuario(usuario), HttpStatus.CREATED);

	}

}
