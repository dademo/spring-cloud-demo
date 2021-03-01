package fr.dademo.test.springclouddemo.processor;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface TestDataProcessor {

    String OUTPUT = "dataOutput";

    @Output(OUTPUT)
    MessageChannel dataOutput();
}
