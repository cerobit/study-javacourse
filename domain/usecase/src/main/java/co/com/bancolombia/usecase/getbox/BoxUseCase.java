package co.com.bancolombia.usecase.getbox;

import co.com.bancolombia.model.event.BoxEvent;
import co.com.bancolombia.model.box.Box;
import co.com.bancolombia.model.box.gateways.BoxRepository;
import co.com.bancolombia.model.events.gateways.EventsGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class BoxUseCase {
    private final BoxRepository boxRepository;
    private final EventsGateway eventsGateway;

    public Mono<Box> getBoxByID(String id) {
        return boxRepository.getBoxByID(id);
    }

public Mono<Box> createBox(Box box) {
    return boxRepository.createBox(box)
            .flatMap(createdBox -> {
                BoxEvent event = new BoxEvent(createdBox, "CREATE", "SUCCESS");
                return eventsGateway.emit(event).thenReturn(createdBox);
            })
            .onErrorResume(e -> {
                BoxEvent event = new BoxEvent(box, "CREATE", "FAILED");
                return eventsGateway.emit(event).then(Mono.error(e));
            });
}

    public Mono<Box> updateBox(String id, Box box) {
        return boxRepository.updateBox(id, box);
    }

    public Mono<Void> deleteBox(String id) {
        return boxRepository.deleteBox(id);
    }

    public Flux<Box> listBox() {
        return boxRepository.listBox();
    }
}