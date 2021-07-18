package com.flaviodiminuto.service;

import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.mapper.SorteioMapper;
import com.flaviodiminuto.repository.SorteioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SorteioService {

    @Inject
    SorteioRepository repository;

    @Inject
    HttpClientService httpClientService;

    Logger logger = LoggerFactory.getLogger(SorteioService.class);

    @Transactional
    public boolean saveIfNotExist(SorteioEntity sorteioEntity){
        if(sorteioEntity  == null )return false;
        Optional<SorteioEntity> sorteio = repository.findByIdOptional(sorteioEntity.getConcurso());
        if(sorteio.isEmpty()) {
            String message = String.format("Adicionando o sorteio %d na base de dados", sorteioEntity.getConcurso());
            logger.info(message);
            repository.persist(sorteioEntity);
            return true;
        }
        return false;
    }

    public Optional<SorteioEntity> getUltimoSorteio() throws IOException, InterruptedException {
        Optional<SorteioEntity> sorteioEntityOptional = repository
                .find("order by concurso desc")
                .firstResultOptional();

        if(sorteioEntityOptional.isPresent()){
            SorteioEntity sorteioEntity = sorteioEntityOptional.get();
            LocalDate dataProximoConcurso = sorteioEntity.getDataProximoConcurso();
            LocalDateTime dataHoraSorteio = LocalDateTime.of(
                    dataProximoConcurso.getYear(),
                    dataProximoConcurso.getMonth(),
                    dataProximoConcurso.getDayOfMonth(),
                    21,
                    0
            );

            // Verifica se o sorteio foi realizado em data anterior
            // ou se já foi sorteado hoje
            if(dataProximoConcurso.isBefore(LocalDate.now())
            || (dataProximoConcurso.isEqual(LocalDate.now()) &&
            LocalDateTime.now().isAfter(dataHoraSorteio)) ){
                logger.info("Novo Sorteio realizado");
                if(atualizaUltimoSorteio())
                    return Optional.of(sorteioEntity);
            }else{
                //retorn a o ultimo sorteio registrado
                return sorteioEntityOptional;
            }
        }else{
            if(atualizaUltimoSorteio())
                return getUltimoSorteio();
        }
        return Optional.empty();
    }

    private boolean atualizaUltimoSorteio() throws IOException, InterruptedException {
        //Buscar e salvar o sorteio mais recente
        HttpResponse<String> response = httpClientService.requisitaUltimoSorteioSincrono();
        SorteioEntity sorteio = SorteioMapper.stringToEntity(response.body());
        return saveIfNotExist(sorteio);
    }

    public List<SorteioEntity> findByDateGreaterThan(LocalDate date, int quantidade) {
        return repository.findByDateGreaterThan(date, quantidade);
    }
}
