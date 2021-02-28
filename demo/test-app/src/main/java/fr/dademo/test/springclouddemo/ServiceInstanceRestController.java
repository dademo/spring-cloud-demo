package fr.dademo.test.springclouddemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ServiceInstanceRestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/service-instances")
    public List<List<ServiceInstance>> serviceInstances() {
        return discoveryClient.getServices()
                .stream()
                .map(service -> discoveryClient.getInstances(service))
                .collect(Collectors.toList());
    }

    @GetMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return discoveryClient.getInstances(applicationName);
    }
}