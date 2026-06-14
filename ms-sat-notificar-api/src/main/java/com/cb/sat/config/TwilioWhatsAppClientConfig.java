package com.cb.sat.config;

import com.cb.sat.app.integration.TwilioWhatsAppClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class TwilioWhatsAppClientConfig {

    @Value("${twilio.account-sid:}")
    private String accountSid;

    @Value("${twilio.auth-token:}")
    private String authToken;

    @Value("${twilio.base-url:https://api.twilio.com}")
    private String twilioBaseUrl;

    @Bean
    public TwilioWhatsAppClient twilioWhatsAppClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(twilioBaseUrl + "/2010-04-01/Accounts/" + accountSid)
                .defaultHeaders(headers -> {
                    headers.setBasicAuth(accountSid, authToken);
                    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                })
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(TwilioWhatsAppClient.class);
    }
}
