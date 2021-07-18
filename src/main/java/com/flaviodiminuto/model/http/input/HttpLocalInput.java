package com.flaviodiminuto.model.http.input;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import javax.annotation.Generated;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "posicao",
        "ganhadores",
        "municipio",
        "uf"
})
@JsonIgnoreProperties({"nomeFatansiaUL", "serie"})
@Generated("jsonschema2pojo")
public class HttpLocalInput implements Serializable {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("posicao")
    private Integer posicao;
    @JsonProperty("ganhadores")
    private Integer ganhadores;
    @JsonProperty("municipio")
    private String municipio;
    @JsonProperty("uf")
    private String uf;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 1361463472099096330L;
}
