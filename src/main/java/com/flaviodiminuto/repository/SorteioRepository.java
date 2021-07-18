package com.flaviodiminuto.repository;

import com.flaviodiminuto.model.entity.SorteioEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class SorteioRepository implements PanacheRepository<SorteioEntity> {

    public List<SorteioEntity> findByDateGreaterThan(LocalDate date, int limit) {
        return find("data_sorteio > ?1", date)
                .page(Page.ofSize(limit))
                .list();
    }
}
