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

import cl.ey.model.Usuario;
import cl.ey.response.ResponseHandler;
import cl.ey.service.UsuarioService;

@RestController
@RequestMapping(value="/usuario", produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController {

	private static final Logger logger = LogManager.getLogger(UsuarioController.class);
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping("/ping1")
	public ResponseEntity<Object> ping2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		  String text = "Ping uno";
		  return ResponseHandler.generateResponse(text, HttpStatus.OK, null);
	}

	// Crea registro Usuario
	@PostMapping("/registra")
	public ResponseEntity<Object> registroUsuario(HttpServletRequest request, @RequestBody @Valid Usuario usuario ) {
	    return usuarioService.processRegistroUsuario(request, usuario)
    			.map(user -> ResponseHandler.generateResponse(
    					"User created",
    					HttpStatus.CREATED,
    					user
				))
				.orElseGet(() -> ResponseHandler.generateResponse(
						"User already inserted",
						HttpStatus.CONFLICT,
						null
				));
	}

}
