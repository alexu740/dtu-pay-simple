package com.rest.start.Adapters;


import com.rest.start.Model.Dto.*;
import jakarta.inject.Inject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
public class MerchantFacade {
    public MerchantFacade() {

    }

    public String createMerchant(RegistrationDto dto) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://merchant-facade:8089/merchants");
        Response response = target.request().post(Entity.entity(dto, MediaType.APPLICATION_JSON));
        return response.readEntity(String.class);
    }

    public String deleteMerchant(String id) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://merchant-facade:8089/merchants/"+id);
        Response response = target.request().delete();
        return response.readEntity(String.class);
    }

    public String initializePayment(PaymentDto dto) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://merchant-facade:8089/merchants/" + dto.getMerchantId() + "/payments");
        Response response = target.request().post(Entity.entity(dto, MediaType.APPLICATION_JSON));
        return response.readEntity(String.class);
    }
}
