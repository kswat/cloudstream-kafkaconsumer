package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@SpringBootApplication
public class CloudstreamKafkaconsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudstreamKafkaconsumerApplication.class, args);
	}

	/**
	 * was looking in anonymous.b32f99b8-fd57-493a-9120-c6d560b82ffe: partitions assigned: [stringProcessor-in-0-0]
	 * changed in props file and function name here sanitizingConsumer
	 * @return
	 */
	@Bean
	public Function<List<UUID>, List<Message<String>>> sanitizingConsumer() {
		return idBatch -> {
			System.out.println("Removed digits from batch of " + idBatch.size());
			return idBatch.stream()
					.map(UUID::toString)
					// Remove all digits from the UUID
					.map(uuid -> uuid.replaceAll("\\d",""))
					.map(noDigitString -> MessageBuilder.withPayload(noDigitString).build())
					.toList();
		};
	}


}
