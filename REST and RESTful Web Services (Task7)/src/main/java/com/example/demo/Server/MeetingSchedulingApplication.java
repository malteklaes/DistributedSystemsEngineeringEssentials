package com.example.demo.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Quelle (generell): https://spring.io/guides/#gettingStarted
 * Quelle (initit): https://start.spring.io/
 * Quelle (speziell): https://spring.io/guides/gs/rest-service/ 
 * use:
 * http://localhost:8080/greeting -> {"id":1,"content":"Hello, World!"}
 * http://localhost:8080/greeting?name=User -> {"id":2,"content":"Hello, User!"}
 * 
 * use via cmd:
 * "curl http://localhost:8080/greeting"
 * "curl http://localhost:8080/greeting?name=User"
 * curl http://localhost:8080/api/scheduleMeetings/greeting?Malte
 */
@SpringBootApplication
public class MeetingSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingSchedulingApplication.class, args);
	}

}
