package com.flaviodiminuto.model.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "sorteio")
public class SorteioEntity {

    @Id
    @Column(nullable = false) private Long concurso;
    @Column(nullable = false, name = "data_apuracao") private LocalDate dataApuracao;
    @Column(name = "data_proximo_sorteio") private LocalDate dataProximoConcurso;
    @Column(nullable = false) private int colunaUm;
    @Column(nullable = false) private int colunaDois;
    @Column(nullable = false) private int colunaTres;
    @Column(nullable = false) private int colunaQuatro;
    @Column(nullable = false) private int colunaCinco;
    @Column(nullable = false) private int colunaSeis;
    @Column(nullable = false) private int quantidadeGanhadoresFaixaUm;
    @Column(nullable = false) private int quantidadeGanhadoresFaixaDois;
    @Column(nullable = false) private int quantidadeGanhadoresFaixaTres;
    @OneToMany(cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "concurso") private List<LocalEntity> cidadeGanhadorFaixaUm;
    @Column(nullable = false) private boolean acumulado;
    @Column(nullable = false) private boolean sorteioespecial;

    // Campos que podem conter valor nulo
    @Column private BigDecimal rateioFaixaUm;
    @Column private BigDecimal rateioFaixaDois;
    @Column private BigDecimal rateioFaixaTres;
    @Column private BigDecimal valorArrecadado;
    @Column private BigDecimal valorEstimatidoProximoConcurso;
    @Column private BigDecimal valorAcumuladoProximoConcurso;
    @Column private String local;

    //Dados que não devem ser incluídos em consultas históricas
    @Column private String observacao;
    @Column private BigDecimal valorAcumuladoConcursoEspecial;
    @Column private BigDecimal valorAcumuladoConcurso05;
    @Column private Integer numeroJogo;
    @Column private Integer tipoPublicacao;
    @Column private Integer numeroConcursoAnterior;
    @Column private Integer numeroConcursoProximo;
}
