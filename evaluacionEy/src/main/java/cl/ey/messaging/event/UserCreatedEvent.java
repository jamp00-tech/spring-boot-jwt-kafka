package cl.ey.messaging.event;

import java.util.UUID;

public record UserCreatedEvent(
		UUID idUsuario,
		String email
	) { }
