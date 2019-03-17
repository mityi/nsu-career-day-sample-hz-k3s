package ru.te.demo;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Random;

@Component
public class GreetingHandler {

    private final HazelcastInstance hz;

    @Autowired
    public GreetingHandler(HazelcastInstance hz) {
        this.hz = hz;
    }

    public Mono<ServerResponse> hello(ServerRequest request) {
        IMap<String, String> map = hz.getMap("grid-db");
        int size = map.size();
        map.put(LocalDateTime.now().toString(), "size:" + size);
        int result;
        if (size == 0) {
            result = 0;
        } else {
            result = new Random().nextInt(size);
        }
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN)
                .body(BodyInserters.fromObject("Hello, Spring! size: " + size + "/ winner: " + result));
    }
}