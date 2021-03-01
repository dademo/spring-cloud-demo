package fr.dademo.test.springclouddemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableDiscoveryClient
@EnableWebMvc
@RefreshScope
public class SpringCloudDemoTestAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudDemoTestAppApplication.class, args);
    }

}
