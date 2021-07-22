package br.com.rest.api;

import javax.net.ssl.HttpsURLConnection;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.rest.model.dto.AlunoIn;
import br.com.rest.model.dto.AlunoOut;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.dto.LoginDTO;
import br.com.rest.model.entity.AlunoEntity;
import br.com.rest.services.AlunoServices;
import br.com.rest.services.TurmaServices;

@Path("/aluno")
public class AlunoApi {

	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public DefaultReturn inserirAluno(AlunoIn aluno, @QueryParam(value = "turmaDefault") Boolean turmaDefault) {
		DefaultReturn dr = AlunoServices.incluirAluno(aluno);
		if((dr.getErros() == null || dr.getErros().size() == 0) && (turmaDefault != null && turmaDefault)) {
			TurmaServices.incluiAlunoTurmaDefault(aluno.getMatricula());
		}
		
		return dr;
	}

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public DefaultReturn realizarLoginPorMatriculaSenha(LoginDTO login) {
		return AlunoServices.findAlunoByMatriculaSenha(login.getMatricula(), login.getSenha());
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response putAluno(@PathParam(value = "id") Long id, AlunoIn aluno) {
		DefaultReturn out;
		Response response = null;
		if(id != null) {
			AlunoEntity alu = AlunoServices.findAlunoByMatricula(aluno.getMatricula());
			if(alu == null)
				out = AlunoServices.alterarAluno(aluno);
			else {
				out = new DefaultReturn();
				out.addErro("Matrícula já existe no banco de dados");
				response = Response.status(HttpsURLConnection.HTTP_BAD_REQUEST).entity(out).build();
			}
		} else {
			out = new DefaultReturn();
			out.addErro("'id' é um parâmetro obrigatório");
			response = Response.status(HttpsURLConnection.HTTP_BAD_REQUEST).entity("'id' é um parâmetro obrigatório").build();
		}
			
		return response;
	}

}
