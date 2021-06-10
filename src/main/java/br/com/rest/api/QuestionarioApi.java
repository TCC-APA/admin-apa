package br.com.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import br.com.rest.model.dto.QuestionarioDTO;
import br.com.rest.services.QuestionarioServices;

@Path("/questionario")
public class QuestionarioApi {

	//Foi colocado o retorno como string, pois o mapa estava convertendo errado
	@GET
	@Path("grupos")
	@Produces(MediaType.APPLICATION_JSON)
	public String consultarQuestionariosPorGruposAluno(@QueryParam(value = "matricula") String matricula) {
		return new Gson().toJson(QuestionarioServices.findQuestionariosPorGruposAluno(matricula));
	}
	
	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String inserirQuestionario(QuestionarioDTO quest) {
		return QuestionarioServices.incluirQuestionario(quest).toString();
	}

}
