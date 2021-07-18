package com.flaviodiminuto.controller;

import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.http.output.HttpSorteioOutput;
import com.flaviodiminuto.model.mapper.SorteioMapper;
import com.flaviodiminuto.repository.SorteioRepository;
import com.flaviodiminuto.service.SorteioService;

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
        if(quantidadeStr.isBlank() || dateStr.isBlank() )
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("data ou valor quantidade invalidos").build();
        LocalDate date = LocalDate.parse(dateStr);
        int quantidade = Integer.parseInt(quantidadeStr);
        List<SorteioEntity> sorteioEntityList = service.findByDateGreaterThan(date, quantidade);

        return Response.ok(sorteioEntityList).build();
    }

}
