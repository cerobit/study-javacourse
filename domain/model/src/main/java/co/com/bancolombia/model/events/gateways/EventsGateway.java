package co.com.bancolombia.model.events.gateways;

import co.com.bancolombia.model.event.BoxEvent;
import co.com.bancolombia.model.event.BoxEventType;
import reactor.core.publisher.Mono;

public interface EventsGateway {
    Mono<Void> emit(BoxEvent event, BoxEventType eventType);
}
