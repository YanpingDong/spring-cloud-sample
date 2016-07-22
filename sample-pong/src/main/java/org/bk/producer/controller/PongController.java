package org.bk.producer.controller;


import javax.management.RuntimeErrorException;

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

@RefreshScope // enable /refresh interface to reset @Value annotation object
@RestController
public class PongController {

	
    @Value("${reply.message:hollo}")
    private String message;


    public Resource<MessageAcknowledgement> fallBackCall(Message message) {
    	Resource<MessageAcknowledgement> fallback =new Resource<>( new MessageAcknowledgement(message.getId(), message.getPayload(), "FAILED SERVICE CALL! - FALLING BACK"));
        return fallback;
    }
    
    @HystrixCommand(fallbackMethod = "fallBackCall")
    @RequestMapping(value = "/message", method = RequestMethod.POST)
    public Resource<MessageAcknowledgement> pongMessage(@RequestBody Message input) {
    	throw new RuntimeException();
       /* return new Resource<>(
                new MessageAcknowledgement(input.getId(), input.getPayload(), message));*/
    }

}