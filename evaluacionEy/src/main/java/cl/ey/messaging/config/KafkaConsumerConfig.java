package cl.ey.messaging.config;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;


@Configuration
public class KafkaConsumerConfig {
	private static final Logger logger = LogManager.getLogger(KafkaConsumerConfig.class);

    @Bean
    public DefaultErrorHandler kafkaErrorHandler() {

        // 3 intentos, esperando 2 segundos entre cada uno
        FixedBackOff fixedBackOff = new FixedBackOff(2000L, 3L);

        return new DefaultErrorHandler((ConsumerRecord<?, ?> record, Exception exception) -> {
        	logger.error("\n\n\tKafka error after retries"
        			+ "\n\tTopic: " + record.topic()
        			+ "\n\tOffset: " + record.offset()
        			+ "\n\tError: " + exception.getMessage());
        }, fixedBackOff);
    }
}