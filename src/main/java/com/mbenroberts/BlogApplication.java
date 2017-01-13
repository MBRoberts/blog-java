package com.mbenroberts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class BlogApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {

		SpringApplication.run(BlogApplication.class, args);

	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(BlogApplication.class);
	}
}
