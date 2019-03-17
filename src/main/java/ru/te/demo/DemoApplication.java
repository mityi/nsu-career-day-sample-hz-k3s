package ru.te.demo;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class DemoApplication {

    @Bean
    public RouterFunction<ServerResponse> route(RandomizerHandler greetingHandler) {
        return RouterFunctions
                .route(RequestPredicates.GET("/all")
                        .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::getAll)
                .and(RouterFunctions
                        .route(RequestPredicates.GET("/status")
                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::getStatus))
                .and(RouterFunctions
                        .route(RequestPredicates.GET("/add/{hash}")
                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::put))
                .and(RouterFunctions
                        .route(RequestPredicates.GET("/winner")
                                .and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::winner));
    }

    @Bean
    public HazelcastInstance hz() {
        return Hazelcast.newHazelcastInstance();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
