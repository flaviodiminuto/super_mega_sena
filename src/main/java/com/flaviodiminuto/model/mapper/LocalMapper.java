package com.flaviodiminuto.model.mapper;

import com.flaviodiminuto.model.entity.LocalEntity;
import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.http.input.HttpLocalInput;
import com.flaviodiminuto.model.http.input.HttpSorteioInput;
import com.flaviodiminuto.model.http.output.HttpLocalOutput;

import java.util.List;
import java.util.stream.Collectors;

public class LocalMapper {
    public static List<LocalEntity> httpInputToEntity(HttpSorteioInput sorteio){
        return sorteio.getListaMunicipioUFGanhadores()
                .stream().map( local ->
                        httpInputToEntity(sorteio.getNumero().longValue(), local))
                .collect(Collectors.toList());
    }
    public static LocalEntity httpInputToEntity(Long idSorteio, HttpLocalInput local){
        return LocalEntity.builder()
                        .idSorteio(idSorteio)
                        .posicao(local.getPosicao())
                        .ganhadores(local.getGanhadores())
                        .municipio(local.getMunicipio())
                        .uf(local.getUf())
                        .build();
    }

    public static List<HttpLocalOutput> entityToHttpOutput(SorteioEntity sorteio){
        return sorteio.getCidadeGanhadorFaixaUm()
                .stream().map(LocalMapper::entityToHttpOutput)
                .collect(Collectors.toList());

    }

    public static HttpLocalOutput entityToHttpOutput(LocalEntity local){
        return HttpLocalOutput.builder()
                .posicao(local.getPosicao())
                .ganhadores(local.getGanhadores())
                .municipio(local.getMunicipio())
                .uf(local.getUf())
                .build();
    }
}
