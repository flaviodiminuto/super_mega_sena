package com.flaviodiminuto.service;

import com.flaviodiminuto.model.mapper.SorteioMapper;
import org.apache.http.conn.ssl.TrustAllStrategy;
import org.apache.http.ssl.SSLContextBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@ApplicationScoped
public class HttpClientService {

    @Inject
    SorteioService atualizaService;

    public HttpRequest getRequest() {
        String urlstr = "https://servicebus2.caixa.gov.br/portaldeloterias/api/megasena";
        return HttpRequest.newBuilder()
                .uri(URI.create(urlstr))
                .GET()
                .build();
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
        HttpClient client = null;
        try {
            client = HttpClient.newBuilder()
                    .sslContext(new SSLContextBuilder().loadTrustMaterial(null, TrustAllStrategy.INSTANCE).build())
                    .build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        client = client == null? HttpClient.newHttpClient() : client;
        return client.send(getRequest(), HttpResponse.BodyHandlers.ofString());
    }
}
