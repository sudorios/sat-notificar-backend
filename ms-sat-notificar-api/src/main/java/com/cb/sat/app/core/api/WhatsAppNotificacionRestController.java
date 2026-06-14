package com.cb.sat.app.core.api;
import com.cb.sat.app.core.facade.WhatsAppNotificacionFacade;
import com.cb.sat.dto.model.BaseOperacionResponse;
import com.cb.sat.dto.model.notificacion.NotificacionMessageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wsp")
@RequiredArgsConstructor
public class WhatsAppNotificacionRestController {

    private final WhatsAppNotificacionFacade whatsAppNotificacionFacade;

    @PostMapping("/sendMessage")
    public BaseOperacionResponse sendMessage(@RequestBody NotificacionMessageRequest request) {
        return whatsAppNotificacionFacade.sendMessage(request);
    }

    @PostMapping("/sendMessageTwilio")
    public BaseOperacionResponse sendMessageTwilio(@RequestBody NotificacionMessageRequest request) {
        return whatsAppNotificacionFacade.sendMessageTwilio(request);
    }
}
