package cl.ey.messaging.consumer;

//import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import cl.ey.messaging.event.UserCreatedEvent;
import jakarta.annotation.PostConstruct;

@Service
public class UserCreatedConsumer {

	private static final Logger logger = LogManager.getLogger(UserCreatedConsumer.class);
	
    @PostConstruct
    public void init() {
    	logger.debug("\n\n\tConsumer BEAN creado\n\n");
    }
	
	@KafkaListener(
		    topics = "user-created",
		    groupId = "test-group"
	)
//	public void consume(ConsumerRecord<String, String> record) {
	public void consume(UserCreatedEvent userCreated) {
		logger.info("\n\n\tKAFKA CONSUMED: " +userCreated.email()+ "\n\n");
	}
}
