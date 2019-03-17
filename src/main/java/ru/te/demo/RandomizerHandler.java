package ru.te.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class RandomizerHandler {


    private final RandomizerService service;

    @Autowired
    public RandomizerHandler(RandomizerService service) {
        this.service = service;
    }

    public Mono<ServerResponse> put(ServerRequest request) {
        long id = service.put(request.pathVariable("hash"));
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject(String.valueOf(id)));
    }

    public Mono<ServerResponse> winner(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject(service.winner()));
    }

    public Mono<ServerResponse> getAll(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject(service.getAll()));
    }

    public Mono<ServerResponse> getStatus(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject(service.getStatus()));
    }
}