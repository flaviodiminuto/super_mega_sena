package com.flaviodiminuto.model.http.input;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "numero",
        "nomeMunicipioUFSorteio",
        "dataApuracao",
        "valorArrecadado",
        "valorEstimadoProximoConcurso",
        "valorAcumuladoProximoConcurso",
        "valorAcumuladoConcursoEspecial",
        "valorAcumuladoConcurso_0_5",
        "acumulado",
        "indicadorConcursoEspecial",
        "dezenasSorteadasOrdemSorteio",
        "numeroJogo",
        "tipoPublicacao",
        "observacao",
        "localSorteio",
        "dataProximoConcurso",
        "numeroConcursoAnterior",
        "numeroConcursoProximo",
        "valorTotalPremioFaixaUm",
        "numeroConcursoFinal_0_5",
        "listaMunicipioUFGanhadores",
        "listaRateioPremio",
        "listaDezenas"
})
@JsonIgnoreProperties({"tipoJogo",
        "listaDezenasSegundoSorteio",
        "listaResultadoEquipeEsportiva",
        "nomeTimeCoracaoMesSorte",
        "id",
        "premiacaoContingencia",
        "exibirDetalhamentoPorCidade",
        "ultimoConcurso",
        "valorSaldoReservaGarantidora"})
@Generated("jsonschema2pojo")
public class HttpSorteioInput {

    @JsonProperty("numero")
    private Integer numero;
    @JsonProperty("nomeMunicipioUFSorteio")
    private String nomeMunicipioUFSorteio;
    @JsonProperty("dataApuracao")
    private String dataApuracao;
    @JsonProperty("valorArrecadado")
    private BigDecimal valorArrecadado;
    @JsonProperty("valorEstimadoProximoConcurso")
    private BigDecimal valorEstimadoProximoConcurso;
    @JsonProperty("valorAcumuladoProximoConcurso")
    private BigDecimal valorAcumuladoProximoConcurso;
    @JsonProperty("valorAcumuladoConcursoEspecial")
    private BigDecimal valorAcumuladoConcursoEspecial;
    @JsonProperty("valorAcumuladoConcurso_0_5")
    private BigDecimal valorAcumuladoConcurso05;
    @JsonProperty("acumulado")
    private Boolean acumulado;
    @JsonProperty("indicadorConcursoEspecial")
    private Integer indicadorConcursoEspecial;
    @JsonProperty("dezenasSorteadasOrdemSorteio")
    @Valid
    private List<String> dezenasSorteadasOrdemSorteio = new ArrayList<String>();
    @JsonProperty("numeroJogo")
    private Integer numeroJogo;
    @JsonProperty("tipoPublicacao")
    private Integer tipoPublicacao;
    @JsonProperty("observacao")
    private String observacao;
    @JsonProperty("localSorteio")
    private String localSorteio;
    @JsonProperty("dataProximoConcurso")
    private String dataProximoConcurso;
    @JsonProperty("numeroConcursoAnterior")
    private Integer numeroConcursoAnterior;
    @JsonProperty("numeroConcursoProximo")
    private Integer numeroConcursoProximo;
    @JsonProperty("valorTotalPremioFaixaUm")
    private Integer valorTotalPremioFaixaUm;
    @JsonProperty("numeroConcursoFinal_0_5")
    private Integer numeroConcursoFinal05;
    @JsonProperty("listaMunicipioUFGanhadores")
    private List<HttpLocalInput> listaMunicipioUFGanhadores = new ArrayList<>();
    @JsonProperty("listaRateioPremio")
    private List<HttpRateioPremioInput> listaHttpRateioPremioInput = new ArrayList<HttpRateioPremioInput>();
    @JsonProperty("listaDezenas")
    private List<String> listaDezenas = new ArrayList<String>();
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private final static long serialVersionUID = 935604446759861270L;

}