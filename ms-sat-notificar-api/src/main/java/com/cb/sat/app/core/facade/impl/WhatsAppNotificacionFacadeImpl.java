package com.cb.sat.app.core.facade.impl;

import com.cb.sat.app.core.facade.WhatsAppNotificacionFacade;
import com.cb.sat.app.integration.NotificacionService;
import com.cb.sat.core.facade.FacadeBase;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.Constantes;
import com.cb.sat.dto.model.notificacion.NotificacionMessageRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class WhatsAppNotificacionFacadeImpl extends FacadeBase implements WhatsAppNotificacionFacade {

    private final NotificacionService metaNotificacionService;
    private final NotificacionService twilioNotificacionService;

    public WhatsAppNotificacionFacadeImpl(
            @Qualifier("metaNotificacionService") NotificacionService metaNotificacionService,
            @Qualifier("twilioNotificacionService") NotificacionService twilioNotificacionService
    ) {
        this.metaNotificacionService = metaNotificacionService;
        this.twilioNotificacionService = twilioNotificacionService;
    }

    @Override
    public BaseOperacionResponse sendMessage(NotificacionMessageRequest request) {
        metaNotificacionService.sendMessage(request.getTelefono(), request.getMensaje());
        return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
    }

    @Override
    public BaseOperacionResponse sendMessageTwilio(NotificacionMessageRequest request) {
        twilioNotificacionService.sendMessage(request.getTelefono(), request.getMensaje());
        return new BaseOperacionResponse(Constantes.SUCCESS, messageSave);
    }
}
