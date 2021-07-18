package com.flaviodiminuto.model.mapper;

import com.flaviodiminuto.constants.DataSpec;
import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.http.input.HttpSorteioInput;
import com.flaviodiminuto.model.http.output.HttpSorteioOutput;

import java.time.LocalDate;
import java.util.Arrays;

public class SorteioMapper {


    public static SorteioEntity httpInputToEntity(HttpSorteioInput sorteio){
        return SorteioEntity.builder()
                .concurso(Long.valueOf(sorteio.getNumero()))
                .local(sorteio.getLocalSorteio())
                .dataApuracao(LocalDate.parse(sorteio.getDataApuracao(), DataSpec.dataformat))
                .dataProximoConcurso(LocalDate.parse(sorteio.getDataProximoConcurso(), DataSpec.dataformat))
                .colunaUm(Integer.parseInt(sorteio.getListaDezenas().get(0)))
                .colunaDois(Integer.parseInt(sorteio.getListaDezenas().get(1)))
                .colunaTres(Integer.parseInt(sorteio.getListaDezenas().get(2)))
                .colunaQuatro(Integer.parseInt(sorteio.getListaDezenas().get(3)))
                .colunaCinco(Integer.parseInt(sorteio.getListaDezenas().get(4)))
                .colunaSeis(Integer.parseInt(sorteio.getListaDezenas().get(5)))
                .quantidadeGanhadoresFaixaUm(sorteio.getListaHttpRateioPremioInput().get(0).getNumeroDeGanhadores())
                .quantidadeGanhadoresFaixaDois(sorteio.getListaHttpRateioPremioInput().get(1).getNumeroDeGanhadores())
                .quantidadeGanhadoresFaixaTres(sorteio.getListaHttpRateioPremioInput().get(2).getNumeroDeGanhadores())
                .rateioFaixaUm(sorteio.getListaHttpRateioPremioInput().get(0).getValorPremio())
                .rateioFaixaDois(sorteio.getListaHttpRateioPremioInput().get(1).getValorPremio())
                .rateioFaixaTres(sorteio.getListaHttpRateioPremioInput().get(2).getValorPremio())
                .cidadeGanhadorFaixaUm(LocalMapper.httpInputToEntity(sorteio))
                .valorEstimatidoProximoConcurso(sorteio.getValorEstimadoProximoConcurso())
                .valorAcumuladoProximoConcurso(sorteio.getValorAcumuladoProximoConcurso())
                .valorAcumuladoConcursoEspecial(sorteio.getValorAcumuladoConcursoEspecial())
                .valorAcumuladoConcurso05(sorteio.getValorAcumuladoConcurso05())
                .acumulado(sorteio.getAcumulado())
                .sorteioespecial(sorteio.getIndicadorConcursoEspecial().equals(1))
                .observacao(sorteio.getObservacao())
                .build();
    }

    public static HttpSorteioOutput entityToHttpOutput(SorteioEntity sorteio) {
//        .dataApuracao(sorteio.getDataApuracao().format(DataSpec.dataformat))
//                .dataProximoConcurso(sorteio.getDataProximoConcurso().format(DataSpec.dataformat))
        return HttpSorteioOutput.builder()
                .concurso(sorteio.getConcurso().intValue())
                .dataApuracao(sorteio.getDataApuracao().format(DataSpec.dataformat))
                .valorArrecadado(sorteio.getValorArrecadado())
                .valorEstimadoProximoConcurso(sorteio.getValorEstimatidoProximoConcurso())
                .valorAcumuladoProximoConcurso(sorteio.getValorAcumuladoProximoConcurso())
                .valorAcumuladoConcursoEspecial(sorteio.getValorAcumuladoConcursoEspecial())
                .valorAcumuladoConcurso05(sorteio.getValorAcumuladoConcurso05())
                .acumulado(sorteio.isAcumulado())
                .indicadorConcursoEspecial(sorteio.isSorteioespecial())
                .listaDezenas(
                    Arrays.asList(String.valueOf(sorteio.getColunaUm()),
                                String.valueOf(sorteio.getColunaDois()),
                                String.valueOf(sorteio.getColunaTres()),
                                String.valueOf(sorteio.getColunaQuatro()),
                                String.valueOf(sorteio.getColunaCinco()),
                                String.valueOf(sorteio.getColunaSeis()))
                )
                .numeroJogo(sorteio.getNumeroJogo())
                .tipoPublicacao(sorteio.getTipoPublicacao())
                .observacao(sorteio.getObservacao())
                .localSorteio(sorteio.getLocal())
                .dataProximoConcurso(sorteio.getDataProximoConcurso().format(DataSpec.dataformat))
                .numeroConcursoAnterior(sorteio.getNumeroConcursoAnterior())
                .numeroConcursoProximo(sorteio.getNumeroConcursoProximo())
                .valorTotalPremioFaixaUm(sorteio.getRateioFaixaUm())
                .listaMunicipioUFGanhadores(LocalMapper.entityToHttpOutput(sorteio))
                .listaHttpRateioPremioOutput(RateioMapper.entityToHttpOutpu(sorteio))
                .build();

    }
}
