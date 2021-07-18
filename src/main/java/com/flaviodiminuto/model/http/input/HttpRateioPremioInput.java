package com.flaviodiminuto.model.http.input;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "faixa",
        "numeroDeGanhadores",
        "valorPremio",
        "descricaoFaixa"
})
@Generated("jsonschema2pojo")
public class HttpRateioPremioInput implements Serializable
{

    @JsonProperty("faixa")
    private Integer faixa;
    @JsonProperty("numeroDeGanhadores")
    private Integer numeroDeGanhadores;
    @JsonProperty("valorPremio")
    private BigDecimal valorPremio;
    @JsonProperty("descricaoFaixa")
    private String descricaoFaixa;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7299392821561272370L;
}