package org.sample.pong.two.producer.controller;

import io.swagger.annotations.ApiOperation;

import java.util.Random;

import org.sample.pong.two.producer.domain.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RefreshScope // enable /refresh interface to reset @Value annotation object
@RestController
public class PongTwoController {
    @Value("${reply.message:hello}")
    private String message;
    
    
    public Message fallBackCall() {
    	Message fallback = new Message("fakeId",  "FAILED SERVICE CALL! - FALLING BACK");
        return fallback;
    }
    
    
    @HystrixCommand(fallbackMethod = "fallBackCall",
	        commandProperties = { 
	            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500"),
	            @HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE")},
	        threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "30"),
                @HystrixProperty(name = "maxQueueSize", value = "101"),
                @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                @HystrixProperty(name = "queueSizeRejectionThreshold", value = "15"),
                @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "12"),
                @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440") })
    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public Message getPongTwoMessage(){
    	Random ra =new Random();
    	int failureSeeds = ra.nextInt(2);
    	if(failureSeeds == 1)
    	{
    		return new Message("Id",  message);
    	}
    	else
    	{
    		throw new RuntimeException();
    	}
    	
    }
}
