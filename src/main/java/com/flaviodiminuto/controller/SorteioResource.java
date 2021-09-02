package com.flaviodiminuto.controller;

import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.http.output.HttpSorteioOutput;
import com.flaviodiminuto.model.mapper.SorteioMapper;
import com.flaviodiminuto.repository.SorteioRepository;
import com.flaviodiminuto.service.SorteioService;
import com.flaviodiminuto.service.WebScrapping;
import org.apache.http.HttpStatus;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Path("/sorteios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SorteioResource {

    @Inject
    SorteioService service;

    @Inject
    WebScrapping webScrapping;

    @ConfigProperty(name = "order.high")
    String high;

    @GET
    @Path("/ultimo")
    public Response buscaUltimoSorteio() throws IOException, InterruptedException {
        Optional<SorteioEntity> sorteio = service.getUltimoSorteio();
        if(sorteio.isPresent()) {
            HttpSorteioOutput sorteioOutput = SorteioMapper.entityToHttpOutput(sorteio.get());
            return Response.ok(sorteioOutput).build();
        } else
            return Response.status(Response.Status.NO_CONTENT).build();
    }

    //exemplo http://localhost:8080/sorteios?data-inicial=2021-06-01&quantidade=3
    @GET
    public Response findByDateGreaterThan(
            @QueryParam("data-inicial") String dateStr,
            @QueryParam("quantidade") String quantidadeStr){
        LocalDate date;
        try{
            if(dateStr.isBlank())
                throw new Exception("Formato de data inválido " + dateStr);
           date = LocalDate.parse(dateStr);
        }catch (Exception e){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("data inválida").build();
        }
        int quantidade = quantidadeStr.isBlank() ? 10 : Integer.parseInt(quantidadeStr);
        List<SorteioEntity> sorteioEntityList = service.findByDateGreaterThan(date, quantidade);
        List<HttpSorteioOutput> output = SorteioMapper.entityToHttpOutput(sorteioEntityList);
        return Response.ok(output).build();
    }

    @POST
    @Path("/adm/salva-historico")
    public Response salvarHistorico(@HeaderParam("high") String highHeader) throws IOException {
        boolean high = this.high.equals(highHeader);
        if(high){
            webScrapping.process();
            return  Response.ok().build();
        }
        return Response.status(HttpStatus.SC_FORBIDDEN).build();
    }

}
