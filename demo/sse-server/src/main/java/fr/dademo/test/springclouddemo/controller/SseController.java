package fr.dademo.test.springclouddemo.controller;

import fr.dademo.test.springclouddemo.service.SseConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.stream.LongStream;

@RestController
public class SseController {

    @Autowired
    private SseConnectorService sseConnectorService;

    @GetMapping(path = "/events")
    public Flux<ServerSentEvent<byte[]>> sseStreamMessages() {
        return sseConnectorService.getFlux();
    }

    @GetMapping(path = "/events2")
    public Flux<ServerSentEvent<String>> sseStreamMessagesTest() {
        return Flux.fromStream(
                LongStream.range(0, 1000)
                        .mapToObj(it ->
                                ServerSentEvent.<String>builder()
                                        .id(String.valueOf(it))
                                        .event("TestEvent")
                                        .data(String.valueOf(it))
                                        .build()
                        ));
    }

    @GetMapping("/stream-sse")
    public Flux<ServerSentEvent<String>> streamEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("periodic-event")
                        .data("SSE - " + LocalTime.now().toString())
                        .build());
    }
}
