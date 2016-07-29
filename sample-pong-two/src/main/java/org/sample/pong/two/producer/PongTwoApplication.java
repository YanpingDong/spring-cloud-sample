package org.sample.pong.two.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

//@SpringCloudApplication //(represent @SpringBootApplication @EnableDiscoveryClient @EnableCircuitBreaker)
@SpringBootApplication()
@EnableDiscoveryClient    //(instead of the old @EnableEurekaClient)
@EnableCircuitBreaker     //(instead of the old @EnableHystrix)
public class PongTwoApplication {
	public static void main(String[] args) {
        SpringApplication.run(PongTwoApplication.class, args);
    }
}
