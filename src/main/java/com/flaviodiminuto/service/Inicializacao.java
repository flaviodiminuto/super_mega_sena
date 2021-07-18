package com.flaviodiminuto.service;

import io.quarkus.runtime.Startup;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;

@ApplicationScoped
@Startup
public class Inicializacao {

    @Inject
    SorteioService service;

    @PostConstruct
    public void start() throws IOException, InterruptedException {
        service.getUltimoSorteio();
    }
}
