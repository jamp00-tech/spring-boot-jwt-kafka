package cl.ey.messaging.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class UserCreatedConsumer {

	private static final Logger logger = LogManager.getLogger(UserCreatedConsumer.class);
	
    @PostConstruct
    public void init() {
    	logger.debug("\n\nConsumer BEAN creado\n\n");
    }
	
	@KafkaListener(
		    topics = "user-created",
		    groupId = "test-group"
	)
	public void consume(ConsumerRecord<String, String> record) {
		logger.info("\n\n");		
		logger.debug("CONSUMED OFFSET: " + record.offset());
	    logger.info("KEY: " + record.key());
	    logger.info("\n\n");
	}
}
