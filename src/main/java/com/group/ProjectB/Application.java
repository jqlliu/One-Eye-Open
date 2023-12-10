package com.group.ProjectB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;


@SpringBootApplication
//@PWA(name = "Hello", shortName = "Hi")


public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}


	@Bean
	Sinks.Many<Message> publisher(){
		return Sinks.many().multicast().onBackpressureBuffer();
	}
	@Bean
	Flux<Message> messages(Sinks.Many<Message> publisher){
		return  publisher.asFlux().replay(30).autoConnect();
	}



}
