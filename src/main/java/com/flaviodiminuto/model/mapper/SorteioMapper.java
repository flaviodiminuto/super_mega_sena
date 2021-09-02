package com.flaviodiminuto.model.mapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flaviodiminuto.constants.DataSpec;
import com.flaviodiminuto.model.entity.LocalEntity;
import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.http.input.HttpSorteioInput;
import com.flaviodiminuto.model.http.output.HttpSorteioOutput;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
                .valorArrecadado(sorteio.getValorArrecadado())
                .sorteioespecial(sorteio.getIndicadorConcursoEspecial().equals(1))
                .observacao(sorteio.getObservacao())
                .build();
    }

    public static HttpSorteioOutput entityToHttpOutput(SorteioEntity sorteio) {
        return HttpSorteioOutput.builder()
                .concurso(sorteio.getConcurso().intValue())
                .dataApuracao(getDateString(sorteio.getDataApuracao()))
                .valorArrecadado(sorteio.getValorArrecadado())
                .valorEstimadoProximoConcurso(sorteio.getValorEstimatidoProximoConcurso())
                .valorAcumuladoProximoConcurso(sorteio.getValorAcumuladoProximoConcurso())
                .valorAcumuladoConcursoEspecial(sorteio.getValorAcumuladoConcursoEspecial())
                .valorAcumuladoConcurso05(sorteio.getValorAcumuladoConcurso05())
                .acumulado(sorteio.isAcumulado())
                .indicadorConcursoEspecial(sorteio.isSorteioespecial())
                .listaDezenas(
                    Arrays.asList(zeroEsquerda(sorteio.getColunaUm()),
                            zeroEsquerda(sorteio.getColunaDois()),
                            zeroEsquerda(sorteio.getColunaTres()),
                            zeroEsquerda(sorteio.getColunaQuatro()),
                            zeroEsquerda(sorteio.getColunaCinco()),
                            zeroEsquerda(sorteio.getColunaSeis()))
                )
                .numeroJogo(sorteio.getNumeroJogo())
                .tipoPublicacao(sorteio.getTipoPublicacao())
                .observacao(sorteio.getObservacao())
                .localSorteio(sorteio.getLocal())
                .dataProximoConcurso(getDateString(sorteio.getDataProximoConcurso()))
                .numeroConcursoAnterior(sorteio.getNumeroConcursoAnterior())
                .numeroConcursoProximo(sorteio.getNumeroConcursoProximo())
                .valorTotalPremioFaixaUm(sorteio.getRateioFaixaUm())
                .listaMunicipioUFGanhadores(LocalMapper.entityToHttpOutput(sorteio))
                .listaHttpRateioPremioOutput(RateioMapper.entityToHttpOutpu(sorteio))
                .build();

    }

    private static String getDateString(LocalDate data){
        return data == null ? null : data.format(DataSpec.dataformat);
    }

    public static SorteioEntity stringToEntity(String response) throws IOException {
        return response.isBlank() ? null :
                httpInputToEntity(stringToHttpInput(response));
    }

    public static HttpSorteioInput stringToHttpInput(String response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return objectMapper.readValue(response.getBytes(StandardCharsets.UTF_8), HttpSorteioInput.class);
    }

    public static SorteioEntity htmlTrToEntity(Element linha){
        return SorteioEntity.builder()
                .concurso(Long.parseLong(getChieldNumber(linha, 0)))
                .local(getChild(linha, 1))
                .dataApuracao(LocalDate.parse(getChild(linha, 2), DataSpec.dataformat))
                .colunaUm(Integer.parseInt(getChieldNumber(linha, 3)))
                .colunaDois(Integer.parseInt(getChieldNumber(linha, 4)))
                .colunaTres(Integer.parseInt(getChieldNumber(linha, 5)))
                .colunaQuatro(Integer.parseInt(getChieldNumber(linha, 6)))
                .colunaCinco(Integer.parseInt(getChieldNumber(linha, 7)))
                .colunaSeis(Integer.parseInt(getChieldNumber(linha, 8)))
                .quantidadeGanhadoresFaixaUm(Integer.parseInt(getChieldNumber(linha, 9)))
                .quantidadeGanhadoresFaixaDois(Integer.parseInt(getChieldNumber(linha, 10)))
                .quantidadeGanhadoresFaixaTres(Integer.parseInt(getChieldNumber(linha, 11)))
                .rateioFaixaUm(new BigDecimal(getChieldNumber(linha, 12)))
                .rateioFaixaDois(new BigDecimal(getChieldNumber(linha, 13)))
                .rateioFaixaTres(new BigDecimal(getChieldNumber(linha, 14)))
                .cidadeGanhadorFaixaUm(
                        getCidades(linha.child(15),
                                Long.parseLong(getChild(linha, 0)),
                                Integer.parseInt(getChieldNumber(linha, 9)))
                )
                .valorArrecadado(new BigDecimal(getChieldNumber(linha, 16)))
                .valorEstimatidoProximoConcurso(new BigDecimal(getChieldNumber(linha, 17)))
                .valorAcumuladoProximoConcurso(new BigDecimal(getChieldNumber(linha, 18)))
                .acumulado(getChild(linha, 19).trim().equalsIgnoreCase("SIM"))
                .sorteioespecial(getChild(linha, 20).trim().equalsIgnoreCase("SIM"))
                .observacao(getChieldNumber(linha, 21))
                .build();
    }

    private static String getChild(Element elemento, int index){
        String texto = elemento.child(index).html().trim();
        return texto.isBlank()? "": texto;
    }

    private static String getChieldNumber(Element elemento, int index){
        String texto = getChild(elemento, index);
        return texto.isBlank()? "0": texto.replace(".", "").replace(",", ".");
    }

    private static List<LocalEntity> getCidades(Element cidades, long idSorteio, int ganhadores){
        List<LocalEntity> locais = new ArrayList<>();
        for (Element cidade : cidades.getElementsByTag("tr")) {
            LocalEntity local = LocalEntity.builder()
                    .idSorteio(idSorteio)
                    .ganhadores(ganhadores)
                    .municipio(getChild(cidade, 0))
                    .uf(getChild(cidade, 1))
                    .build();
            locais.add(local);
        }
        return locais;
    }

    private static String zeroEsquerda(int value){
        return value < 10 ? "00"+value : "0"+value;
    }

    public static List<HttpSorteioOutput> entityToHttpOutput(List<SorteioEntity> entityList){
        return entityList.stream().map(SorteioMapper::entityToHttpOutput).collect(Collectors.toList());
    }
}
