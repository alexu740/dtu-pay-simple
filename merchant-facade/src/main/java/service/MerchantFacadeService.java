package service;

import messaging.Event;

import messaging.MessageQueue;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import adapters.EventPublisher;
import dto.RegistrationDto;

public class MerchantFacadeService {
    private EventPublisher publisher;
    private Map<String, CompletableFuture<String>> correlations = new ConcurrentHashMap<>();

    public MerchantFacadeService(EventPublisher publisher) {
        this.publisher = publisher;
    }

    public String create(RegistrationDto request) {
        var correlationId = CorrelationId.randomId();
		correlations.put(correlationId.get(),new CompletableFuture<String>());

        publisher.emitCreateUserEvent("asd", correlationId);
        return correlations.get(correlationId.get()).join();
    }

    public void completeRegistration(String eventPayload, CorrelationId correlationId) {
        System.out.println(correlationId.get());
        System.out.println(eventPayload);
		correlations.get(correlationId.get()).complete(eventPayload);
    }
    
    public void remove() {
        //queue.publish(new Event("MerchantRegistrationRequested"))
    }
}