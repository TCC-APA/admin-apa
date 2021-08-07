package br.com.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.services.TurmaServices;

@Path("/turma")
public class TurmaApi {
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public DefaultReturn findTurmas(@QueryParam(value = "idProfessor") Long idProfessor) {
		return TurmaServices.buscarTurmasFiltroProfessor(idProfessor);
	}
	
	@GET
	@Path("filtro")
	@Produces(MediaType.APPLICATION_JSON)
	public DefaultReturn findTurmasFiltroProf(@QueryParam(value = "idProfessor") Long idProfessor) {
		return TurmaServices.buscarTurmasFiltroProfessorSimplified(idProfessor);
	}
	
	@GET
	@Path("inclui/{codigoTurma}/aluno/{matricula}")
	@Produces(MediaType.APPLICATION_JSON)
	public DefaultReturn incluiAlunoTurma(@PathParam(value = "codigoTurma") String codigoTurma,
										  @PathParam(value = "matricula") String matricula) {
		return TurmaServices.incluiAlunoTurma(matricula, codigoTurma);
	}
	

}
