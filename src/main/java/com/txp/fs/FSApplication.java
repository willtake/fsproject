package com.txp.fs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@ComponentScan
@EnableScheduling
public class FSApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
	    SpringApplication app = new SpringApplication(FSApplication.class);
	    app.setShowBanner(true);
	    app.run(args);
	}
}
