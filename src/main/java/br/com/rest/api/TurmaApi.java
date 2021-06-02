package br.com.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.services.TurmaServices;

@Path("/turma")
public class TurmaApi {
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public DefaultReturn findTurmas(@PathParam(value = "idProfessor") Long idProfessor) {
		return TurmaServices.buscarTurmasFiltroProfessor(idProfessor);
		
	}
	

}
