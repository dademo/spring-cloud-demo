package fr.dademo.test.springclouddemo.producer;

import fr.dademo.test.springclouddemo.processor.TestDataProcessor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@EnableBinding(TestDataProcessor.class)
@Component
public class TestDataProducer {

    private static final Logger logger = LoggerFactory.getLogger(TestDataProducer.class);
    private static final String KEY_PRODUCER_NAME = "apiProducer";

    @Value("${app.producerName}")
    private String producerName;
    @Value("${app.serviceUrl}")
    private String serviceUrl;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TestDataProcessor processor;

    public void pollFromService() {
        logger.info("Fetching values from URL [{}]", serviceUrl);
        if(processor.dataOutput().send(messageWithPayload(performAndRetrieveRequest()))) {
            logger.info("Message successfully sent");
        } else {
            logger.warn("An error occurred when sending message");
        }
        logger.info("Done !");
    }

    private byte[] performAndRetrieveRequest() {
        return restTemplate.execute(serviceUrl, HttpMethod.GET, this::requestCallback, this::requestExtractor);
    }

    private void requestCallback(ClientHttpRequest request) {
        // Nothing
    }

    @SneakyThrows
    private byte[] requestExtractor(ClientHttpResponse response) {

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody().readAllBytes();
        }
        throw new APIFetchException(serviceUrl, response.getStatusCode());
    }

    private Message<byte[]> messageWithPayload(byte[] payload) {
        return MessageBuilder
                .withPayload(payload)
                //.setHeader(KEY_PRODUCER_NAME, producerName)
                .build();
    }
}
