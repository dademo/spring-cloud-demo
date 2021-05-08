package fr.dademo.test.springclouddemo.producer;

import fr.dademo.test.springclouddemo.dto.PostDto;
import fr.dademo.test.springclouddemo.entities.PostEntity;
import fr.dademo.test.springclouddemo.entities.mappers.PostMapper;
import fr.dademo.test.springclouddemo.processor.TestDataProcessor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@EnableBinding(TestDataProcessor.class)
@Component
public class TestDataProducer {

    private static final Logger logger = LoggerFactory.getLogger(TestDataProducer.class);

    @Value("${app.producerName}")
    private String producerName;
    @Value("${app.serviceUrl}")
    private String serviceUrl;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TestDataProcessor processor;
    @Autowired
    private PostMapper mapper;

    public void pollFromService() {
        logger.info("Fetching values from URL [{}]", serviceUrl);
        if (processor.dataOutput().send(messageWithPayload(performAndRetrieveRequestEntity()))) {
            logger.info("Message successfully sent");
        } else {
            logger.warn("An error occurred when sending message");
        }
        logger.info("Done !");
    }

    private List<PostDto> performAndRetrieveRequestEntity() {

        val response = restTemplate.getForEntity(serviceUrl, PostEntity[].class);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new APIFetchException(serviceUrl, response.getStatusCode());
        }
        return Optional.ofNullable(response.getBody()).stream().flatMap(Arrays::stream)
                .map(mapper::mapToPostDto)
                .collect(Collectors.toList());
    }

    private <T> Message<T> messageWithPayload(T payload) {
        return MessageBuilder
                .withPayload(payload)
                //.setHeader(KEY_PRODUCER_NAME, producerName)
                .build();
    }
}
