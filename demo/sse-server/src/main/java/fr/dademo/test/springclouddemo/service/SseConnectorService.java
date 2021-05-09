package fr.dademo.test.springclouddemo.service;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Service
public class SseConnectorService {

    @Autowired
    private PublishSubscribeChannel internalSseMessageChannel;

    public Flux<ServerSentEvent<byte[]>> getFlux() {
        return new QueueHandlerFlux(internalSseMessageChannel);
    }

    @RequiredArgsConstructor
    private static class QueueHandlerFlux extends Flux<ServerSentEvent<byte[]>> {

        private final PublishSubscribeChannel sseInputChannel;

        @Override
        public void subscribe(CoreSubscriber<? super ServerSentEvent<byte[]>> actual) {
            actual.onSubscribe(new QueueHandlerFluxSubscription(actual));
        }

        private class QueueHandlerFluxSubscription implements Subscription, MessageHandler {

            private static final long UNBOUNDED_TIMEOUT_MILLIS = 100L;

            private final Subscriber<? super ServerSentEvent<byte[]>> subscriber;

            private final QueueChannel subscriptionMessageChannel = new QueueChannel();
            private boolean isCanceled = false;


            QueueHandlerFluxSubscription(Subscriber<? super ServerSentEvent<byte[]>> subscriber) {
                this.subscriber = subscriber;
                sseInputChannel.subscribe(this);
            }

            @Override
            public void request(long n) {

                if (n == Long.MAX_VALUE) {
                    sendUnbounded();
                } else {
                    sendNext();
                }
            }

            @Override
            public void cancel() {

                isCanceled = true;
                sseInputChannel.unsubscribe(this);
                subscriptionMessageChannel.destroy();
                subscriptionMessageChannel.clear();
                subscriber.onComplete();
            }

            @Override
            public void handleMessage(@NonNull Message<?> message) throws MessagingException {
                subscriptionMessageChannel.send(message);
            }

            private void sendNext() {
                next(-1);
            }

            private void sendUnbounded() {
                while (!isCanceled) {
                    next(UNBOUNDED_TIMEOUT_MILLIS);
                }
            }

            private void next(long timeout) {
                try {
                    messagePayloadOf(subscriptionMessageChannel.receive(timeout))
                            .ifPresent(subscriber::onNext);
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }

            @SuppressWarnings("unchecked")
            private Optional<ServerSentEvent<byte[]>> messagePayloadOf(@Nullable Message<?> message) {
                return (Optional<ServerSentEvent<byte[]>>) Optional.ofNullable(message)
                        .map(Message::getPayload);
            }
        }
    }
}
