package com.cb.sat.app.integration.impl;

import com.cb.sat.app.integration.NotificacionService;
import com.cb.sat.app.integration.WhatsAppClient;
import com.cb.sat.core.exception.InternalException;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.whatsapp.WhatsAppMessageRequest;
import com.cb.sat.dto.model.whatsapp.WhatsAppTextRequest;
import com.cb.sat.dto.util.GenericUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("metaNotificacionService")
@RequiredArgsConstructor
@Slf4j
public class WhatsAppNotificacionServiceImpl implements NotificacionService {

    private final WhatsAppClient whatsAppClient;

    @Override
    public void sendMessage(String telefono, String mensaje) {
        WhatsAppTextRequest text = new WhatsAppTextRequest(false, mensaje);
        WhatsAppMessageRequest request = new WhatsAppMessageRequest(
                Constantes.WhatsAppConfig.WHATSAPP,
                GenericUtil.limpiarTelefono(telefono),
                Constantes.WhatsAppConfig.TEXT,
                text
        );
        try {
            whatsAppClient.sendMessage(request);
        } catch (Exception e) {
            log.error("Fallo al contactar con la API de WhatsApp. Causa: {}", e.getMessage());
            throw new InternalException(
                    "Error al enviar mensaje por WhatsApp",
                    "No se pudo enviar la notificación. Verifique el número o intente nuevamente.",
                    "WHATSAPP_SEND_ERROR",
                    e
            );
        }
    }
}
