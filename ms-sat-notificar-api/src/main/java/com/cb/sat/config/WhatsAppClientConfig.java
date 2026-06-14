package com.cb.sat.config;

import com.cb.sat.app.integration.WhatsAppClient;
import com.cb.sat.dto.model.Constantes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class WhatsAppClientConfig {

    @Value("${whatsapp.token}")
    private String whatsAppToken;

    @Value("${whatsapp.url}")
    private String whatsAppUrl;

    @Bean
    public WhatsAppClient whatsAppClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(whatsAppUrl)
                .defaultHeader(SecurityUtil.HEADER_STRING, SecurityUtil.TOKEN_PREFIX + whatsAppToken) //
                .defaultHeader(SecurityUtil.CONTENT_TYPE, Constantes.ContentType.JSON)
                .build();

        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(WhatsAppClient.class);
    }
}