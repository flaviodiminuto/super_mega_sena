package com.flaviodiminuto.service;

import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.mapper.SorteioMapper;
import com.flaviodiminuto.repository.SorteioRepository;
import io.quarkus.runtime.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
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
    public Optional<SorteioEntity> saveIfNotExist(SorteioEntity sorteioEntity){
        return repository.saveIfNotExistis(sorteioEntity);
    }

    public Optional<SorteioEntity> getUltimoSorteio() throws IOException, InterruptedException {
        Optional<SorteioEntity> sorteioEntityOptional = repository
                .find("order by concurso desc")
                .firstResultOptional();

        if(sorteioEntityOptional.isPresent()) {
            SorteioEntity sorteioEntity = sorteioEntityOptional.get();
            LocalDate dataProximoConcurso = sorteioEntity.getDataProximoConcurso();
            LocalDateTime dataHoraSorteio = LocalDateTime.of(
                    dataProximoConcurso.getYear(),
                    dataProximoConcurso.getMonth(),
                    dataProximoConcurso.getDayOfMonth(),
                    21,
                    0
            );

            // Verifica proximo será depois de hoje
            // ou se é hoje mas ainda não chegou o horário
            if (dataProximoConcurso.isAfter(LocalDate.now())
                    || (dataProximoConcurso.isEqual(LocalDate.now()) &&
                    LocalDateTime.now().isBefore(dataHoraSorteio))) {
                //retorna o ultimo sorteio registrado
                return sorteioEntityOptional;
            }

            logger.info("Sorteio Realizado, os dados de ultimo sorteio serão atualizadoss");
        }
        logger.info("Atualizando os dados do Ultimo Sorteio");
        return atualizaUltimoSorteio();
    }

    private Optional<SorteioEntity> atualizaUltimoSorteio() throws IOException, InterruptedException {
        logger.info("Buscando sorteio atualizado na Caixa - " + LocalDateTime.now());
        HttpResponse<String> response = httpClientService.requisitaUltimoSorteioSincrono();
        SorteioEntity sorteio = SorteioMapper.stringToEntity(response.body());
        return saveIfNotExist(sorteio);
    }

    public List<SorteioEntity> findByDateGreaterThan(LocalDate date, int quantidade) {
        return repository.findByDateGreaterThan(date, quantidade);
    }
}
