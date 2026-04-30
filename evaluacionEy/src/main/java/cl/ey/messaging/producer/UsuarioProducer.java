package cl.ey.messaging.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import cl.ey.model.Usuario;
import jakarta.annotation.PostConstruct;

@Service
public class UsuarioProducer {

	private static final Logger logger = LogManager.getLogger(UsuarioProducer.class);

    private static final String TOPIC = "user-created";
    private final KafkaTemplate<String, Usuario> kafkaTemplate;

    @PostConstruct
    public void init() {
    	logger.debug("\n\nProducer BEAN creado\n\n");
    }

    public UsuarioProducer(KafkaTemplate<String, Usuario> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreated(Usuario usuario) {
        kafkaTemplate.send(TOPIC, usuario.getEmail(), usuario)
        .whenComplete((result, ex) -> {
            if (ex != null) {
            	logger.info("\n\nKAFKA ERROR: " +ex.getMessage());
            } else {
            	logger.info("\n\nKAFKA PRODUCER SENT OK -> topic: "
                        + result.getRecordMetadata().topic()
                        + ", partition: "
                        + result.getRecordMetadata().partition()
                        + ", offset: "
                        + result.getRecordMetadata().offset()
                        + "\n\n");
            }
        });

    }
}
