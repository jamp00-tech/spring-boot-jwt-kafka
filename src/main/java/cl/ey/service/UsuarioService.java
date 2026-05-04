package cl.ey.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.ey.messaging.producer.UsuarioProducer;
import cl.ey.model.LoginRequest;
import cl.ey.model.Usuario;
import cl.ey.repository.UsuariosRepository;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class UsuarioService {

	private static final Logger logger = LogManager.getLogger(UsuarioService.class);
	private final String PREFIX = "Bearer ";
	private final String HEADER = "Authorization";

	@Autowired
	private UsuariosRepository usuariosRepository;
	
	@Autowired
	private UsuarioProducer usuarioProducer;

	public Optional<Usuario> processRegistroUsuario(HttpServletRequest request, Usuario usuario) {
		logger.debug("UsuarioService::processRegistroUsuario(HttpServletRequest, Usuario)");
		
		if (existEmail(usuario.getEmail()))
			return Optional.empty();

		String authorization = request.getHeader(HEADER);

		if (authorization == null || !authorization.startsWith(PREFIX))
		    throw new IllegalArgumentException("Invalid authorization header");

		String jwtToken = authorization.replace(PREFIX, "");

		usuario.setToken(jwtToken);
		Usuario savedUser = registraUsuario(usuario);

		usuarioProducer.sendUserCreated(savedUser);

		return Optional.of(savedUser);
	}

	private Usuario registraUsuario(Usuario usuario ) {
		return usuariosRepository.save(usuario);
	}

	private boolean existEmail(String email) {

		Usuario usuario = usuariosRepository.findByEmail(email);
		Optional<Usuario> usuarioOp = Optional.ofNullable(usuario);

		return (usuarioOp.isPresent())? true: false;

	}

	public boolean validUserPass(LoginRequest login) {
		logger.debug("UsuarioService::validUserPass(LoginRequest)");
	    if (!"a@a.com".equals(login.getEmail()) || !"Ab12".equals(login.getPassword()))
	        return false;

		return true;
	}
}
