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
	public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {

		return RouterFunctions
				.route(RequestPredicates.GET("/hello")
						.and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), greetingHandler::hello);
	}

	@Bean
	public HazelcastInstance hz() {
		return Hazelcast.newHazelcastInstance();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
