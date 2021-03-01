package fr.dademo.test.springclouddemo.processor;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface TestDataProcessor {

    String INPUT = "dataInput";

    @Input(INPUT)
    SubscribableChannel dataInput();
}
