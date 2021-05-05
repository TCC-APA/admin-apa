package br.com.rest.api;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/monitoracao")
public class MonitoracaoApi {

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String ping() {
		return new Date() + ": Status de servidor: ok!";
	}
}

