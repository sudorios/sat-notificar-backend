package com.cb.sat.app.integration.impl;

import com.cb.sat.app.integration.NotificacionService;
import com.cb.sat.app.integration.TwilioWhatsAppClient;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.util.GenericUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Component("twilioNotificacionService")
@RequiredArgsConstructor
@Slf4j
public class TwilioNotificacionServiceImpl implements NotificacionService {

    private static final String WHATSAPP_PREFIX = "whatsapp:+";
    private static final String WHATSAPP_PREFIX_2P = "whatsapp:";

    private final TwilioWhatsAppClient twilioWhatsAppClient;

    @Value("${twilio.whatsapp-from:whatsapp:+14155238886}")
    private String whatsAppFrom;

    @Override
    public void sendMessage(String telefono, String mensaje) {
        try {
            MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
            request.add(Constantes.TwilioConfig.TO, formatWhatsAppNumber(telefono));
            request.add(Constantes.TwilioConfig.FROM, whatsAppFrom);
            request.add(Constantes.TwilioConfig.BODY, mensaje);

            twilioWhatsAppClient.sendMessage(request);
        } catch (Exception e) {
            log.error("Fallo al contactar con la API de Twilio. Causa: {}", e.getMessage());
            throw new InternalException("Error al enviar mensaje por Twilio", "No se pudo enviar la notificación. Verifique el número o intente nuevamente.");
        }
    }

    private String formatWhatsAppNumber(String telefono) {
        if (GenericUtil.isNotNull(telefono) && telefono.startsWith(WHATSAPP_PREFIX_2P)) {
            return telefono;
        }
        return WHATSAPP_PREFIX + GenericUtil.limpiarTelefono(telefono);
    }
}
