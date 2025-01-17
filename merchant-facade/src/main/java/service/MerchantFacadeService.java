package service;

import boilerplate.Event;

import boilerplate.MessageQueue;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import adapters.EventPublisher;
import dto.PaymentDto;
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

        publisher.emitCreateUserEvent(request, correlationId);
        return correlations.get(correlationId.get()).join();
    }

    public void completeRegistration(String eventPayload, CorrelationId correlationId) {
        System.out.println(correlationId.get());
        System.out.println(eventPayload);
        var promise = correlations.get(correlationId.get());
        if(promise != null) {
            promise.complete(eventPayload);
        }
    }

    public String initialisePayment(PaymentDto dto) {
        var correlationId = CorrelationId.randomId();
		correlations.put(correlationId.get(),new CompletableFuture<String>());

        publisher.emitInitialisePayment(dto, correlationId);
        return correlations.get(correlationId.get()).join();
    }
    
    public void remove() {
        //queue.publish(new Event("MerchantRegistrationRequested"))
    }
}
