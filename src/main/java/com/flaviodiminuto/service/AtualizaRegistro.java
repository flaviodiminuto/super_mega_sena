package com.flaviodiminuto.service;

import com.flaviodiminuto.model.entity.SorteioEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class AtualizaRegistro {

    @Inject
    EntityManager em;

    @Transactional
    public boolean saveIfNotExist(SorteioEntity sorteioEntity){
        if(em.find(SorteioEntity.class, sorteioEntity.getConcurso()) == null) {
            em.persist(sorteioEntity);
            return true;
        }
        return false;
    }
}
