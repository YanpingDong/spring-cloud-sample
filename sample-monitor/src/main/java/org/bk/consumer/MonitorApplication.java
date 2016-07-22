package org.bk.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;

@SpringBootApplication
@EnableDiscoveryClient   //(instead of the old @EnableEurekaClient)

/*
 * This application is playing the role of 
 * both showing the Hystrix Dashboard and exposing turbine stream. 
 */
//role hystrix ashoard
@EnableHystrixDashboard  // Dashboard displays the health of each circuit breaker in an efficient manner
                         // You then visit /hystrix and point the dashboard to 
                         // an individual instances /hystrix.stream endpoint in a Hystrix client application.
//role turbine stream
@EnableTurbine           // see detail info  refer to https://github.com/Netflix/Turbine/wiki/Configuration-(1.x)
public class MonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitorApplication.class, args);
    }

}
