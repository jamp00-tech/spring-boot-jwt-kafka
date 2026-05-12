package cl.ey.messaging.consumer;

//import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import cl.ey.messaging.event.KafkaDemoState;
import cl.ey.messaging.event.UserCreatedEvent;
import jakarta.annotation.PostConstruct;

@Service
public class UserCreatedConsumer {

	private static final Logger logger = LogManager.getLogger(UserCreatedConsumer.class);
	
    private static final String TOPIC = "user-created";

    @Autowired
    private KafkaDemoState kafkaDemoState;

    @PostConstruct
    public void init() {
    	logger.debug("\n\n\tConsumer BEAN creado\n\n");
    }
	
	@KafkaListener(
		    topics = TOPIC,
		    groupId = "demo-group"
	)
//	public void consume(ConsumerRecord<String, String> record) {
	public void consume(UserCreatedEvent userCreated) {
		logger.info("\n\n\tKAFKA CONSUMED: " +userCreated.email()+ "\n\n");

		if (userCreated.email().contains("error"))
			throw new RuntimeException("Errar handling KAFKA CONSUMER");

		String kafkaMessage = "\n\n\tUser created event processed: " +userCreated.email()+ "\n";
    	logger.info(kafkaMessage);
    	kafkaDemoState.setLastConsumedEvent(kafkaMessage);

	}
}
