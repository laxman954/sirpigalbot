package org.sirpigal.slack.init;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * @author lperumalm
 *
 */
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

}
