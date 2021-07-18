package com.flaviodiminuto.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CsvService {
//    private final String arquivo_path = "src/main/resources/movies.csv";
    private final String arquivo_path = "C:\\SEM VERGONHA\\super_mega_sena\\src\\main\\resources\\historico.csv";
    private final String BASE_INSERT = "INSERT INTO sorteio (%s) VALUES %s";
    private final String REMOVE_ALL = "DELETE FROM sorteio"; // Remover todos os registros
    private final String campos = preparaCampos();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final int[] colunas = {0, 2, 9, 10, 11, 3, 4, 5, 6, 7, 8};
    @Inject
    EntityManager em;
    private boolean primeiraLinha = true;

    private  File getArquivo(){
        return new File(arquivo_path);
    }

    private CSVReader getCsvReader() throws FileNotFoundException {
        return new CSVReader(new FileReader(arquivo_path));
    }

    @Transactional
    public void persistirSorteiosDoArquivo() throws IOException, CsvValidationException {
        List<String[]> linhas = getLinhasDoCsv();
           StringBuilder valueBuilder= new StringBuilder();
        linhas.forEach(linha -> {
            if(linha[0].isEmpty() || linha[0].equals(";") || primeiraLinha) {
                primeiraLinha = false;
                return;
            }
            valueBuilder.append(" (");
            valueBuilder.append(preparaValores(linha));
            valueBuilder.append("),\n");
        });
        String values = valueBuilder.substring(0, valueBuilder.length()-2);//remover a virgula e quebra de linha final
        String sql = String.format(BASE_INSERT, campos, values);
        logger.info(sql);
        commit(REMOVE_ALL);
        commit(sql);
        primeiraLinha = true;
    }

    public List<String[]> getLinhasDoCsv() throws IOException, CsvValidationException {
        CSVReader reader = getCsvReader();
        String[] nextLine;
        List<String[]> linhas = new ArrayList<>();

        while ((nextLine = reader.readNext()) != null){
            linhas.add(nextLine);
        }
        return linhas;
    }

    private String preparaCampos(){
        StringBuilder builder = new StringBuilder();
        addCampo(builder, "concurso");
        addCampo(builder, "data_sorteio");
        addCampo(builder, "ganhadores_mega");
        addCampo(builder, "ganhadores_quina");
        addCampo(builder, "ganhadores_quadra");
        addCampo(builder, "numero_sorteado_1");
        addCampo(builder, "numero_sorteado_2");
        addCampo(builder, "numero_sorteado_3");
        addCampo(builder, "numero_sorteado_4");
        addCampo(builder, "numero_sorteado_5");
        addCampo(builder, "numero_sorteado_6");
        return builder.substring(0, builder.length()-2);
    }

    /**
     *
     * @param linhaValor
     * @return string no padrao 'value1', 'value2', ..., valueX
     */
    private String preparaValores(String[] linhaValor) {
        StringBuilder values = new StringBuilder();
        for (int i = 0; i < colunas.length; i++) {
            if (i != 1) {
                values.append(linhaValor[colunas[i]]);
            } else {
                addData(values, linhaValor[colunas[i]]);
            }
            if(i < linhaValor.length-1){ //se NÃO for o ultimo registro da linha
                    values.append(", ");
            }
        }
        return values.substring(0, values.length()-2);
    }
    private void addCampo(StringBuilder builder, String campo){
        builder.append(campo)
                .append(", ");
    }

    private void addData(StringBuilder builder, String value){
        String[] split = value.split("/");
        String data = String.format("%s-%s-%s", split[2], split[1], split[0]);
        builder.append("'").append(data).append("'");
    }

    @Transactional
    private void commit(String insert) {
        Query query = em.createNativeQuery(insert);
        query.executeUpdate();
        em.flush();
    }
}
