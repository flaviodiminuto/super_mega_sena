package com.flaviodiminuto.service;

import com.opencsv.exceptions.CsvValidationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;

@ApplicationScoped
public class AdminService {
    @Inject CsvService csvService;

    @Transactional
    public boolean atualizarBase() throws IOException, CsvValidationException {
        csvService.persistirSorteiosDoArquivo();
        return true;
    }
}
