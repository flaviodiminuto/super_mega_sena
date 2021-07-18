package com.flaviodiminuto.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "local_ganhador")
public class LocalEntity implements Serializable {
    private final static long serialVersionUID = 7299392821561272371L;
    @Id
    @Column(name = "id_sorteio") private Long idSorteio;
    @Id @Column private String municipio;
    @Id @Column private String uf;
    @Column private Integer posicao;
    @Column private Integer ganhadores;
}
