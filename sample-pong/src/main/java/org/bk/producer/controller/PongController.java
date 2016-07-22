package org.bk.producer.controller;


import java.util.Random;

import org.bk.producer.domain.Message;
import org.bk.producer.domain.MessageAcknowledgement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RefreshScope // enable /refresh interface to reset @Value annotation object
@RestController
public class PongController {

	
    @Value("${reply.message:hollo}")
    private String message;


    public Resource<MessageAcknowledgement> fallBackCall(Message message) {
    	Resource<MessageAcknowledgement> fallback =new Resource<>( new MessageAcknowledgement(message.getId(), message.getPayload(), "FAILED SERVICE CALL! - FALLING BACK"));
        return fallback;
    }
    
    public MessageAcknowledgement fallBackCall() {
    	MessageAcknowledgement fallback = new MessageAcknowledgement("fakeId", "fakePayload", "FAILED SERVICE CALL! - FALLING BACK");
        return fallback;
    }
    
    /*
     * See more Hystrix details refer https://github.com/Netflix/Hystrix/tree/master/hystrix-contrib/hystrix-javanica#configuration
     */
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
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public Resource<MessageAcknowledgement> pongMessage(@RequestBody Message input) {
    	throw new RuntimeException();
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
    public MessageAcknowledgement getMessage(){
    	Random ra =new Random();
    	int failureSeeds = ra.nextInt(2);
    	if(failureSeeds == 1)
    	{
    		return new MessageAcknowledgement("Id", message, "SUCCESS");
    	}
    	else
    	{
    		throw new RuntimeException();
    	}
    	
    }
}