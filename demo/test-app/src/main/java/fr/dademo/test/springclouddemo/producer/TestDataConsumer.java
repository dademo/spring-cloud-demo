package fr.dademo.test.springclouddemo.producer;

import fr.dademo.test.springclouddemo.processor.TestDataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(TestDataProcessor.class)
public class TestDataConsumer {

    private static final Logger logger = LoggerFactory.getLogger(TestDataConsumer.class);

    @StreamListener(TestDataProcessor.INPUT)
    public void processValue(Message<?> message) {
        logger.info("Received message !");
        logger.info("Received data [{}]", message.getPayload());
    }
}
