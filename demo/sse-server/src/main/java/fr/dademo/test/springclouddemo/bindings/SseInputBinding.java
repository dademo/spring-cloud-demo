package fr.dademo.test.springclouddemo.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface SseInputBinding {

    String INPUT = "inputSseMessage";

    @Input(INPUT)
    SubscribableChannel sseInputMessages();
}
