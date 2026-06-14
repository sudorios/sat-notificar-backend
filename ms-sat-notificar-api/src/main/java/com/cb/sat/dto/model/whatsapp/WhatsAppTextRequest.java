package com.cb.sat.dto.model.whatsapp;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppTextRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6673361257584757423L;

    @JsonProperty("preview_url")
    private Boolean previewUrl;

    private String body;
}
