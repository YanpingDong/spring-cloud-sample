package org.bk.consumer.service;

import org.bk.consumer.domain.Message;
import org.bk.consumer.domain.MessageAcknowledgement;
import org.bk.consumer.feign.PongClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
@EnableFeignClients
@Service("hystrixPongClient")
public class HystrixWrappedPongClient implements PongClient {

    @Autowired
    private PongClient feignPongClient;
    
    @Override
    @HystrixCommand(fallbackMethod = "fallBackCall")
    public MessageAcknowledgement sendMessage(Message message) {
        return this.feignPongClient.sendMessage(message);
    }

    public MessageAcknowledgement fallBackCall(Message message) {
        MessageAcknowledgement fallback = new MessageAcknowledgement(message.getId(), message.getPayload(), "FAILED SERVICE CALL! - FALLING BACK");
        return fallback;
    }
}
