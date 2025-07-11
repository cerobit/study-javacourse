package co.com.bancolombia.usecase.getbox;

import co.com.bancolombia.model.event.BoxEvent;
import co.com.bancolombia.model.box.Box;
import co.com.bancolombia.model.box.gateways.BoxRepository;
import co.com.bancolombia.model.event.BoxEventType;
import co.com.bancolombia.model.event.BoxEventUpdate;
import co.com.bancolombia.model.events.gateways.EventsGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

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
                    BoxEvent event = new BoxEvent("CREATE", "SUCCESS", createdBox );
                    return eventsGateway.emit(event, BoxEventType.CREATE ).thenReturn(createdBox);
                })
                .onErrorResume(e -> {
                    BoxEvent event = new BoxEvent( "CREATE", "FAILED", box);
                    return eventsGateway.emit(event, BoxEventType.CREATE).then(Mono.error(e));
                });
    }

public Mono<Box> updateBoxName(String id, String name) {
    return boxRepository.getBoxByID(id)
        .flatMap(boxBefore ->
            boxRepository.updateName(id, name)
                .flatMap(updatedBox -> {
                    BoxEventUpdate event = new BoxEventUpdate().toBuilder()
                            .previousName(boxBefore.getName())
                            .newName(name)
                            .updatedAt(new java.sql.Timestamp(System.currentTimeMillis()))
                            .build();
                    return eventsGateway.emitEventUpdate(event, BoxEventType.UPDATE).thenReturn(updatedBox);
                })
                .onErrorResume(e -> {
                    BoxEventUpdate event = new BoxEventUpdate().toBuilder()
                            .previousName(boxBefore.getName())
                            .newName(name)
                            .updatedAt(new java.sql.Timestamp(System.currentTimeMillis()))
                            .build();
                    return eventsGateway.emitEventUpdate(event, BoxEventType.UPDATE).then(Mono.error(e));
                })
        );
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