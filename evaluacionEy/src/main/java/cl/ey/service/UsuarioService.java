package cl.ey.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import cl.ey.model.LoginRequest;

@Component
public class UsuarioService {
	private static final Logger logger = LogManager.getLogger(UsuarioService.class);
	
	public boolean validUserPass(LoginRequest login) {
		
	    if (!"a@a.com".equals(login.getEmail()) || !"Ab12".equals(login.getPassword()))
	        return false;

		return true;
	}
}
