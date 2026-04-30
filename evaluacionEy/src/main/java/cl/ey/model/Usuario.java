package cl.ey.model;

import java.time.LocalDate;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Usuario {

	@Id
    @GeneratedValue
    @UuidGenerator
    private UUID usuarioId;

	@Email(message = "Email incorrecto")
	private String email;

	@Pattern(regexp = "^(?=.*((\\S*\\d){2,}))(?=.*((\\S*[A-Z]){1,}))(?=.*[a-z])\\S{4,}$", message = "Contraseña incorrecta")
	private String password;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="phones")
	private List<Phones> phones;
	

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate created;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate modified;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate lastLogin;

	private String token;

	private boolean isActive;

	public Usuario() {
		this.created = LocalDate.now();
		this.modified = LocalDate.now();
		this.lastLogin = LocalDate.now();

		this.isActive = true;
	}

	public Usuario(String email, String password, List<Phones> phones, String token) {
		super();
		this.email = email;
		this.password = password;
		this.token = token;
		this.phones = phones;
	}

	public List<Phones> getPhones() {
		return phones;
	}

	public void setPhones(List<Phones> phones) {
		this.phones = phones;
	}

	public UUID getIdUsuario() {
		return usuarioId;
	}

	public void setIdUsuario(UUID usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getCreated() {
		return created;
	}

	public void setCreated(LocalDate created) {
		this.created = created;
	}

	public LocalDate getModified() {
		return modified;
	}

	public void setModified(LocalDate modified) {
		this.modified = modified;
	}

	public LocalDate getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDate lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}


	@Override
	public String toString() {
		return "Usuarios [usuarioId=" + usuarioId + ", email=" + email + ", password=" + password + ", created="
				+ created + ", modified=" + modified + ", lastLogin=" + lastLogin + ", token=" + token + ", phones="
				+ phones + "]";
	}

	
}
