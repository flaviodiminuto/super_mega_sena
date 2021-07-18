package com.flaviodiminuto.model.http.output;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "faixa",
        "numero_de_ganhadores",
        "valor_premio",
        "descricao_faixa"
})
@Generated("jsonschema2pojo")
public class HttpRateioPremioOutput implements Serializable
{

    @JsonProperty("faixa")
    private Integer faixa;
    @JsonProperty("numero_de_ganhadores")
    private Integer numeroDeGanhadores;
    @JsonProperty("valor_premio")
    private BigDecimal valorPremio;
    @JsonProperty("descricao_faixa")
    private String descricaoFaixa;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 7299392821561272370L;

}