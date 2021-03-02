package fr.dademo.test.springclouddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTask
public class SpringCloudDemoTaskPollerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemoTaskPollerApplication.class, args).close();
    }

    @Bean
    public RestTemplate beanRestTemplate() {
        return new RestTemplate();
    }

}
