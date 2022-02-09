/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webservicies.apis;

import com.google.gson.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ejb.backend.appdata.Methods;
import com.webservicies.controller.CustomerController;

/**
 * REST Web Service
 *
 * @author dcarvajals
 */
@Path("customer")
public class CustomerApis {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CustomerApis
     */
    public CustomerApis() { }
    
    @POST
    public Response insertCustomer(@Context HttpHeaders headers, String data) {
        String message = "";
        System.out.println("insertCustomer()");
        JsonObject jso = Methods.stringToJSON(data);
        if (jso.size() > 0) {
            String lastname = Methods.JsonToString(jso, "lastname", "");
            String name = Methods.JsonToString(jso, "name", "");
            String email = Methods.JsonToString(jso, "email", "");
            String address = Methods.JsonToString(jso, "address", "");
            
            CustomerController ccon = new CustomerController();
            if(ccon.insertCustomer(lastname, name, email, address)){
                message = Methods.getJsonMessage("2", "Cliente agregado correctamente.", "{}");
            } else {
                message = Methods.getJsonMessage("3", "El cliente no pude ser agregado.", "{}");
            }
        } else {
            message = Methods.getJsonMessage("4", "faltan los parámetros.", "{}");
        }
        return Response.ok(message).build();
    }


}
