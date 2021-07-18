package com.flaviodiminuto.controller;

import com.flaviodiminuto.model.entity.SorteioEntity;
import com.flaviodiminuto.model.http.output.HttpSorteioOutput;
import com.flaviodiminuto.model.mapper.SorteioMapper;
import com.flaviodiminuto.repository.SorteioRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Path("/sorteios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SorteioResource {

    @Inject
    SorteioRepository repository;

    @GET
    @Path("/ultimo")
    public Response buscaUltimoSorteio(){
        Optional<SorteioEntity> sorteio = repository.find("order by concurso desc").firstResultOptional();
        if(sorteio.isPresent()) {
            HttpSorteioOutput sorteioOutput = SorteioMapper.entityToHttpOutput(sorteio.get());
            return Response.ok(sorteioOutput).build();
        } else
            return Response.status(Response.Status.NOT_FOUND).build();
    }

    //exemplo http://localhost:8080/sorteios?data=2020-09-15&quantidade=12
    @GET
    public Response findByDateGreaterThan(
            @QueryParam("data") String dateStr,
            @QueryParam("quantidade") String quantidadeStr){
        if(quantidadeStr == null || dateStr == null )
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("data ou valor quantidade invalidos").build();
        LocalDate date = LocalDate.parse(dateStr);
        int quantidade = Integer.parseInt(quantidadeStr);
        List<SorteioEntity> sorteioEntityList = repository.findByDateGreaterThan(date, quantidade);
        Function<List<SorteioEntity>, Response> sorteioPreenchido = (list) -> Response.ok(list).build();
        Function<List<SorteioEntity>, Response> verificaListaVazia = (list) -> list.isEmpty() ?
                Response.status(Response.Status.NOT_FOUND).build() :
                sorteioPreenchido.apply(list);

        return verificaListaVazia.apply(sorteioEntityList);
    }

}
