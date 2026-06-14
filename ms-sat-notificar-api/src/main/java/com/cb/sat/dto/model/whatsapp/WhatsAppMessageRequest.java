package com.cb.sat.dto.model.whatsapp;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WhatsAppMessageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 6188184676072353162L;

    @JsonProperty("messaging_product")
    private String messagingProduct;
    private String to;
    private String type;
    private WhatsAppTextRequest text;
}
