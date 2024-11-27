package com.auth.mfa;

import com.auth.mfa.controller.ControllerUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MfaApplication {
	private final static Logger LOGGER = LoggerFactory.getLogger(MfaApplication.class);

	public static void main(String[] args) {
		LOGGER.info("Starting mfa api");
		SpringApplication.run(MfaApplication.class, args);
		LOGGER.info("mfa api started");
	}
}