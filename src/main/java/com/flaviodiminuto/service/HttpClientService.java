package com.flaviodiminuto.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flaviodiminuto.model.http.input.HttpSorteioInput;
import com.flaviodiminuto.model.mapper.SorteioMapper;
import io.quarkus.runtime.Startup;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@ApplicationScoped
@Startup
public class HttpClientService {

    @Inject
    ObjectMapper mapper;

    @Inject
    AtualizaRegistro atualizaService;

    @PostConstruct
    public void requisitaUltimoSorteio(){
        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        final long timestamp = System.currentTimeMillis();
        String urlstr = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado/c=cacheLevelPage/?timestampAjax=";
        urlstr += timestamp;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Cookie", "security=true")
                .uri(URI.create(urlstr))
                .build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    response = response
                            .replaceAll("\\s+","")
                            .trim();
                    try {
                        HttpSorteioInput httpSorteioInput = mapper.readValue(response.getBytes(StandardCharsets.UTF_8), HttpSorteioInput.class);
                        atualizaService.saveIfNotExist(SorteioMapper.httpInputToEntity(httpSorteioInput));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .join();
    }
}
