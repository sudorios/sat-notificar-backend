package com.cb.sat.dto.model.notificacion;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class NotificacionMessageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6409415055020159306L;

    private String telefono;
    private String mensaje;
}
