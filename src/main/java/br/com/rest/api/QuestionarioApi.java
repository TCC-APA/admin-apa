package br.com.rest.api;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONObject;

import br.com.rest.model.dto.EstiloDTO;
import br.com.rest.model.dto.QuestionarioDTO;
import br.com.rest.services.QuestionarioServices;

@Path("/questionario")
public class QuestionarioApi {

	@GET
	@Path("grupos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<QuestionarioDTO> consultarQuestionariosPorGruposAluno(@QueryParam(value = "matricula") String matricula) {
		return QuestionarioServices.findQuestionariosPorGruposAluno(matricula);
	}
	
	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public String inserirQuestionario(QuestionarioDTO quest) {
		return QuestionarioServices.incluirQuestionario(quest).toString();
	}

}
