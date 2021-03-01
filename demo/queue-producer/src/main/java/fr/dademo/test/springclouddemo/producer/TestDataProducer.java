package fr.dademo.test.springclouddemo.producer;

import fr.dademo.test.springclouddemo.processor.TestDataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@EnableBinding(TestDataProcessor.class)
@Component
public class TestDataProducer {

    private static final Logger logger = LoggerFactory.getLogger(TestDataProducer.class);

    @Value("${app.generatedCount}")
    private int generatedCount;

    @Autowired
    private TestDataProcessor processor;

    public void produce() {
        logger.info("Generating {} message(s)...", generatedCount);
        IntStream.rangeClosed(0, generatedCount)
                .forEach(it -> processor.dataOutput().send(this.messageUsingId(it)));
        logger.info("Done !");
    }

    private Message<?> messageUsingId(int id) {
        return MessageBuilder.withPayload(String.format("Body using id %d", id)).build();
    }
}
