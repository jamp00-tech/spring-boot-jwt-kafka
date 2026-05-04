package cl.ey.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import cl.ey.model.Usuario;

@Repository
public interface UsuariosRepository extends CrudRepository<Usuario, Serializable> {

	public Usuario findByEmail(String email);

}

