package com.cb.sat.app.integration;

import com.cb.sat.dto.model.whatsapp.WhatsAppMessageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(url = "/messages")
public interface WhatsAppClient {

    @PostExchange
    void sendMessage(@RequestBody WhatsAppMessageRequest request);

}
