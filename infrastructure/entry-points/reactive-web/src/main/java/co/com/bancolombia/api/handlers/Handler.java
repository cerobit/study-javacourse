package co.com.bancolombia.api.handlers;

import co.com.bancolombia.model.box.Box;
import co.com.bancolombia.usecase.getbox.BoxUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class Handler {

    private final BoxUseCase boxUseCase;

    public Mono<ServerResponse> getBoxByID(ServerRequest request) {
        String id = request.pathVariable("id");
        return boxUseCase.getBoxByID(id)
                .flatMap(box -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(box))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createBox(ServerRequest request) {
        return request.bodyToMono(Box.class)
                .flatMap(boxUseCase::createBox)
                .flatMap(box -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(box));
    }

    public Mono<ServerResponse> updateBox(ServerRequest request) {
        String id = request.pathVariable("id");
        return request.bodyToMono(Box.class)
                .flatMap(box -> boxUseCase.updateBox(id, box))
                .flatMap(box -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(box))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> deleteBox(ServerRequest request) {
        String id = request.pathVariable("id");
        return boxUseCase.deleteBox(id)
                .then(ServerResponse.noContent().build());
    }

    public Mono<ServerResponse> listBox(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(boxUseCase.listBox(), Box.class);
    }

}