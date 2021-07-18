package com.flaviodiminuto.controller;

import com.flaviodiminuto.service.AdminService;
import com.opencsv.exceptions.CsvValidationException;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/admin")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PersistirSorteio {
    @Inject AdminService adminService;

    @POST
    @Path("/carga")
    public Response persistirArquivo() throws IOException, CsvValidationException {
        if(adminService.atualizarBase())
            return Response.ok("Base atualizada").build();
        else
            return Response.serverError().entity("Não foi possivel atualizar a base").build();
    }
}
