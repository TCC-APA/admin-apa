package br.com.rest.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.sun.istack.NotNull;

import br.com.rest.model.dto.InsereProfessorIn;
import br.com.rest.model.entity.ProfessorEntity;
import br.com.rest.services.ProfessorServices;

@Path("/prof")
public class ProfessorApi {

	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String inserirProfessor(InsereProfessorIn professor) {
		return ProfessorServices.incluirProfessor(professor).toString();
	}

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public ProfessorEntity buscarProfessorPorSiape(@NotNull @QueryParam(value = "siape") String siape) {
		return ProfessorServices.consultarProfessorPorSiape(siape);
	}
	
	@DELETE
	@Path("/{siape}")
	@Produces(MediaType.APPLICATION_JSON)
	public String deletarProfessorPorSiape(@PathParam(value = "siape") String siape) {
		return ProfessorServices.deleteProfessorPorSiape(siape).toString();
	}

}
