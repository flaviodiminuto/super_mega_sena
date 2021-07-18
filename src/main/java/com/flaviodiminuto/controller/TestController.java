package com.flaviodiminuto.controller;

import com.flaviodiminuto.service.HttpClientService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/test")
public class TestController {
    @Inject
    HttpClientService service;

    @GET
    public Response get(){
        service.requisitaUltimoSorteioAssincrono();
        return Response.ok().build();
    }
}
