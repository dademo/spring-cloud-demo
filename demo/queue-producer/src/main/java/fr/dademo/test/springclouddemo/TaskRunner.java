package fr.dademo.test.springclouddemo;

import fr.dademo.test.springclouddemo.producer.TestDataProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TaskRunner implements CommandLineRunner {

    @Autowired
    private TestDataProducer producer;

    @Override
    public void run(String... args) throws Exception {
        producer.produce();
    }
}
