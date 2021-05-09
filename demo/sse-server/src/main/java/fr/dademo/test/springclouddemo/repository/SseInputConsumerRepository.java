package fr.dademo.test.springclouddemo.repository;

import fr.dademo.test.springclouddemo.bindings.SseInputBinding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Repository;

@EnableBinding(SseInputBinding.class)
@Repository
public class SseInputConsumerRepository {

    private static final Logger logger = LoggerFactory.getLogger(SseInputConsumerRepository.class);

    private static int messageIds = 0;

    @Autowired
    private PublishSubscribeChannel internalSseMessageChannel;

    @StreamListener(SseInputBinding.INPUT)
    public void processValue(Message<?> message) {

        logger.info("Received message !");
        logger.info("Received data [{}]", message.getPayload());

        this.internalSseMessageChannel.send(mappedMessageToEvent(message));
    }

    private Message<ServerSentEvent<String>> mappedMessageToEvent(Message<?> source) {
        return MessageBuilder
                .withPayload(serverSentEventOf(source))
                .build();
    }

    private static ServerSentEvent<String> serverSentEventOf(Message<?> source) {
        return ServerSentEvent.<String>builder()
                .id(String.valueOf(messageIds++))
                .data(source.getPayload().toString())
                .event("SseMessage")
                .build();
    }
}
