package org.bk.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

//@SpringCloudApplication //(represent @SpringBootApplication @EnableDiscoveryClient @EnableCircuitBreaker)
@SpringBootApplication()
@EnableDiscoveryClient //(instead of the old @EnableEurekaClient)
@EnableCircuitBreaker //(instead of the old @EnableHystrix)
@EnableHystrixDashboard
public class PongApplication {
    public static void main(String[] args) {
        SpringApplication.run(PongApplication.class, args);
    }
}
