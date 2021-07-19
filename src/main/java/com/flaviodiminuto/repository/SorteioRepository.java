package com.flaviodiminuto.repository;

import com.flaviodiminuto.model.entity.SorteioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SorteioRepository implements PanacheRepository<SorteioEntity> {

    public List<SorteioEntity> findByDateGreaterThan(LocalDate date, int limit) {
        return find("data_apuracao > ?1", date)
                .page(Page.ofSize(limit))
                .list();
    }

    public Optional<SorteioEntity> saveIfNotExistis(SorteioEntity sorteioEntity) {
        if(sorteioEntity  == null )return Optional.empty();
        Optional<SorteioEntity> sorteio = findByIdOptional(sorteioEntity.getConcurso());
        if(sorteio.isEmpty()) {
            persist(sorteioEntity);
            return Optional.of(sorteioEntity);
        }
        return sorteio;
    }
}
