package com.micro.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("hi")
public class SayHiResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("h")
    public List<String> sayHi() {
        List<String> result = new ArrayList<String>();
        result.add("hello spring boot");
        result.add("hello micro services");
        return result;
    }
}
