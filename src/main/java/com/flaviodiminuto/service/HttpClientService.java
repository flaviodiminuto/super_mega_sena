package com.flaviodiminuto.service;

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

@ApplicationScoped
public class HttpClientService {

    @Inject
    SorteioService atualizaService;

    public HttpRequest getRequest() {
        final long timestamp = System.currentTimeMillis();
        String urlstr = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0KO6H80AU71KG7J0072/res/id=buscaResultado/c=cacheLevelPage/?timestampAjax=";
        urlstr += timestamp;
        HttpRequest request = HttpRequest.newBuilder()
                .header("Cookie", "security=true")
                .uri(URI.create(urlstr))
                .build();
        return request;
    }

    public void requisitaUltimoSorteioAssincrono() {
        HttpClient client = HttpClient.newHttpClient();
        client.sendAsync(getRequest(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(response -> {
                    response = response
                            .replaceAll("\\s+", "")
                            .trim();
                    try {
                        atualizaService.saveIfNotExist(SorteioMapper.stringToEntity(response));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                .join();
    }

    public HttpResponse<String> requisitaUltimoSorteioSincrono() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        return client.send(getRequest(), HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getHistoricoCaixa(){
        String url = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0K8DBC0QPVN93KQ10G1/res/id=historicoHTML/c=cacheLevelPage/=/";
        return null;
    }

}
