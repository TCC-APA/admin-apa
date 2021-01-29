package br.com.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.rest.model.dto.QuestionarioDTO;
import br.com.rest.model.entity.QuestionarioEntity;
import br.com.rest.services.QuestionarioServices;

@Path("/questionario")
public class QuestionarioApi {

	@GET
	@Path("grupos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<QuestionarioEntity> consultarQuestionariosPorGruposAluno(@QueryParam(value = "matricula") String matricula) {
		return QuestionarioServices.buscarQuestionariosPorGruposAluno(matricula);
	}
	
	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String inserirQuestionariosPorGruposAluno(QuestionarioDTO quest) {
		return QuestionarioServices.incluirQuestionario(quest).toString();
	}

}
