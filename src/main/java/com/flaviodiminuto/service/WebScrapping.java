package com.flaviodiminuto.service;

import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.mapper.SorteioMapper;
import com.flaviodiminuto.repository.SorteioRepository;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class WebScrapping {

    @Inject
    SorteioRepository repository;

    @Inject
    SorteioService service;

    Logger logger = LoggerFactory.getLogger(WebScrapping.class);

    private static final String url = "http://loterias.caixa.gov.br/wps/portal/loterias/landing/megasena/!ut/p/a1/04_Sj9CPykssy0xPLMnMz0vMAfGjzOLNDH0MPAzcDbwMPI0sDBxNXAOMwrzCjA0sjIEKIoEKnN0dPUzMfQwMDEwsjAw8XZw8XMwtfQ0MPM2I02-AAzgaENIfrh-FqsQ9wNnUwNHfxcnSwBgIDUyhCvA5EawAjxsKckMjDDI9FQE-F4ca/dl5/d5/L2dBISEvZ0FBIS9nQSEh/pw/Z7_HGK818G0K8DBC0QPVN93KQ10G1/res/id=historicoHTML/c=cacheLevelPage/=/";

    @Transactional
    public void process() throws IOException {
        logger.info("############################");
        logger.info("Iniciando a persistencia do histórico dos sorteios");
        Elements linhas = getLinhas();
        boolean cabecalho = true;
        long contador = 0;

        if(linhas.isEmpty()) return;

        repository.getEntityManager().flush();
        for (Element linha:linhas){
            if(cabecalho){
                cabecalho = false;
                continue;
            }
            // evita a tentativa de adição das linhas que
            // somente tem os nomes das cidades
            // como entidades do tipo sorteio
            if(linha.childNodeSize() > 5) {
                contador++;
                //Converte a linha para um objeto do tipo SorteioEntity
                SorteioEntity sorteio = SorteioMapper.htmlTrToEntity(linha);
                service.saveIfNotExist(sorteio);
//                 Persistir no banco e zerar a lista
//                 a cada duzentos registros
                if(contador % 400 == 0){
                    repository.flush();
                    String mensagem = String.format("Foram persistidos %d registros", contador);
                    logger.info(mensagem);
                }
            }
        }
        //persiste o último conjunto de registros
        repository.flush();
        String mensagem = String.format("Foram persistidos %d registros", contador);
        logger.info(mensagem);
        logger.info("Todos os registros foram adicionados a base de dados");
        logger.info("############################");
    }

    private Elements getLinhas() throws IOException {
        Connection connection = Jsoup.connect(url)
                .cookie("security", "true")
                .maxBodySize(685000000)
                .ignoreContentType(true);
        Connection.Response response = connection.execute();
        String body = sanitized(response.body());
        Document doc = Jsoup.parseBodyFragment(body);

        return doc.getElementsByTag("tr");
    }

    private String sanitized(String string) {
        return string
                .replaceAll("\\\\x\\p{XDigit}{2}", "")
                .replaceAll("\\P{Print}", "");
    }
}
