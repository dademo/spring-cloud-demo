package fr.dademo.test.springclouddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@SpringBootApplication
@EnableTask
public class SpringCloudDemoTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemoTaskApplication.class, args).close();
    }

}
