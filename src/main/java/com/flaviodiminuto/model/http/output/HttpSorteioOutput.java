package com.flaviodiminuto.model.http.output;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

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
public class HttpSorteioOutput {
    private final static long serialVersionUID = 935604446759861272L;
    @JsonProperty("concurso")
    private Integer concurso;
    @JsonProperty("nome_municipio_uf_sorteio")
    private String nomeMunicipioUFSorteio;
    @JsonProperty("data_apuracao")
    private String dataApuracao;
    @JsonProperty("valor_arrecadado")
    private BigDecimal valorArrecadado;
    @JsonProperty("valor_estimado_proximo_concurso")
    private BigDecimal valorEstimadoProximoConcurso;
    @JsonProperty("valor_acumulado_proximo_concurso")
    private BigDecimal valorAcumuladoProximoConcurso;
    @JsonProperty("valor_acumulado_concurso_especial")
    private BigDecimal valorAcumuladoConcursoEspecial;
    @JsonProperty("valor_acumulado_concurso_05")
    private BigDecimal valorAcumuladoConcurso05;
    @JsonProperty("is_acumulado")
    private Boolean acumulado;
    @JsonProperty("is_concurso_especial")
    private Boolean indicadorConcursoEspecial;
    @JsonProperty("dezenasSorteadasOrdemSorteio")
    @Valid
    private List<String> dezenasSorteadasOrdemSorteio = new ArrayList<String>();
    @JsonProperty("numero_jogo")
    private Integer numeroJogo;
    @JsonProperty("tipo_publicacao")
    private Integer tipoPublicacao;
    @JsonProperty("observacao")
    private String observacao;
    @JsonProperty("local_sorteio")
    private String localSorteio;
    @JsonProperty("data_proximo_concurso")
    private String dataProximoConcurso;
    @JsonProperty("numero_concurso_anterior")
    private Integer numeroConcursoAnterior;
    @JsonProperty("numero_concurso_proximo")
    private Integer numeroConcursoProximo;
    @JsonProperty("valor_total_premio_faixa_um")
    private BigDecimal valorTotalPremioFaixaUm;
    @JsonProperty("numero_concurso_final_05")
    private Integer numeroConcursoFinal05;
    @JsonProperty("lista_municipio_uf_ganhadores")
    private List<HttpLocalOutput> listaMunicipioUFGanhadores = new ArrayList<>();
    @JsonProperty("lista_rateio_premio")
    private List<HttpRateioPremioOutput> listaHttpRateioPremioOutput = new ArrayList<HttpRateioPremioOutput>();
    @JsonProperty("lista_dezenas")
    private List<String> listaDezenas = new ArrayList<String>();
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}