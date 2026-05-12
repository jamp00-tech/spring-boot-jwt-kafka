package cl.ey.messaging.producer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import cl.ey.messaging.event.KafkaDemoState;
import cl.ey.messaging.event.UserCreatedEvent;
import cl.ey.model.Usuario;
import jakarta.annotation.PostConstruct;

@Service
public class UsuarioProducer {

	private static final Logger logger = LogManager.getLogger(UsuarioProducer.class);

    private static final String TOPIC = "user-created";
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    @Autowired
    private KafkaDemoState kafkaDemoState;

    @PostConstruct
    public void init() {
    	logger.debug("\n\n\tProducer BEAN creado\n\n");
    }

    public UsuarioProducer(KafkaTemplate<String, UserCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserCreated(Usuario usuario) {
    	
    	UserCreatedEvent userCreated = new UserCreatedEvent(
    			usuario.getIdUsuario(),
    			usuario.getEmail()
    			);
    	
        kafkaTemplate.send(TOPIC, userCreated.email(), userCreated)
        .whenComplete((result, ex) -> {
            if (ex != null) {
            	logger.info("\n\nKAFKA ERROR: " +ex.getMessage());
            } else {
            	String kafkaMessage = "\n\n\tKAFKA PRODUCER SENT OK -> topic: "
                        + result.getRecordMetadata().topic()
                        + ", partition: "
                        + result.getRecordMetadata().partition()
                        + ", offset: "
                        + result.getRecordMetadata().offset()
                        + "\n\n";
            			
            	logger.info(kafkaMessage);
            	
            	kafkaDemoState.setLastProducedEvent(kafkaMessage);
            	
            }
        });

    }
}
