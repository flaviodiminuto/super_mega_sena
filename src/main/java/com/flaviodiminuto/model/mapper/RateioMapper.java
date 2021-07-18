package com.flaviodiminuto.model.mapper;

import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.http.output.HttpRateioPremioOutput;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RateioMapper {

    public static List<HttpRateioPremioOutput> entityToHttpOutpu(SorteioEntity sorteio){
        List<HttpRateioPremioOutput> rateioList = new ArrayList<>();
        rateioList.add(entityToHttpOutpu(1,
                sorteio.getQuantidadeGanhadoresFaixaUm(),
                sorteio.getRateioFaixaUm(),""));
        rateioList.add(entityToHttpOutpu(2,
                sorteio.getQuantidadeGanhadoresFaixaDois(),
                sorteio.getRateioFaixaDois(), ""));
        rateioList.add(entityToHttpOutpu(3,
                sorteio.getQuantidadeGanhadoresFaixaTres(),
                sorteio.getRateioFaixaTres(), ""));
        return rateioList;
    }

    public static HttpRateioPremioOutput entityToHttpOutpu(Integer faixa,
                                                    Integer numeroDeGanhadores,
                                                    BigDecimal valorPremio,
                                                    String descricaoFaixa){
        return HttpRateioPremioOutput.builder()
                .faixa(faixa)
                .numeroDeGanhadores(numeroDeGanhadores)
                .valorPremio(valorPremio)
                .descricaoFaixa(descricaoFaixa)
                .build();
    }
}
