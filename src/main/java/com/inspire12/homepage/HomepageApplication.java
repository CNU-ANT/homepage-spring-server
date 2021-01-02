package com.inspire12.homepage;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class HomepageApplication {

	public static void main(String[] args){
        SpringApplication application = new SpringApplication(HomepageApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
	}
}
