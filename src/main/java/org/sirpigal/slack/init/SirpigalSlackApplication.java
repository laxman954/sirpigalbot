package org.sirpigal.slack.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lperumalm
 *
 */
@RestController
@EnableAutoConfiguration
public class SirpigalSlackApplication {
	/**
	 * Entry point of the application. Run this method to start the slack
	 * applications
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(SirpigalSlackApplication.class, args);
	}
	
	@RequestMapping("/")
	public String index() {
		return "Greetings from Sirpigal Bot!";
	}

}
