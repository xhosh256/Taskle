package xhosh.dev.taskle;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Key;

@SpringBootApplication
public class TaskleApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskleApplication.class, args);

	}
}
