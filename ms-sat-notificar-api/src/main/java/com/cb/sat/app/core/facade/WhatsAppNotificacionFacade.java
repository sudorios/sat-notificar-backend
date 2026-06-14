package com.cb.sat.app.core.facade;

import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.notificacion.NotificacionMessageRequest;

public interface WhatsAppNotificacionFacade {
    BaseOperacionResponse sendMessage(NotificacionMessageRequest request);
    BaseOperacionResponse sendMessageTwilio(NotificacionMessageRequest request);
}
