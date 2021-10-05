package br.com.rest.api;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.rest.model.dto.AlunoIn;
import br.com.rest.model.dto.AlunoSimplifiedOut;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.dto.LoginDTO;
import br.com.rest.services.AlunoServices;
import br.com.rest.services.TurmaServices;

@Path("/aluno")
public class AlunoApi {

	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public DefaultReturn inserirAluno(AlunoIn aluno, @QueryParam(value = "turmaDefault") Boolean turmaDefault) {
		DefaultReturn dr = AlunoServices.incluirAluno(aluno);
		if((dr.getErros() == null || dr.getErros().size() == 0) && (turmaDefault != null && turmaDefault)) {
			TurmaServices.incluiAlunoTurmaDefault(aluno.getMatricula());
		}
		
		return dr;
	}

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public DefaultReturn realizarLoginPorMatriculaSenha(LoginDTO login) {
		return AlunoServices.findAlunoByMatriculaSenha(login.getMatricula(), login.getSenha());
	}
	
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public Response putAluno(@PathParam(value = "id") Long id, AlunoIn aluno) {
		DefaultReturn out;
		Response response = null;
		if(id != null && id > 0) {
			if(aluno != null) {
				try {
					AlunoServices.alterarAluno(id, aluno);
					response = Response.status(HttpsURLConnection.HTTP_NO_CONTENT).build();
				} catch(Exception e) {
					out = new DefaultReturn();
					out.addErro("ocorreu um erro ao alterar o aluno");
					e.printStackTrace();
					response = Response.status(HttpsURLConnection.HTTP_BAD_REQUEST).entity(out).build();
				}
				
			} else {
				out = new DefaultReturn();
				out.addErro("AlunoIn nao foi enviado no body");
				response = Response.status(HttpsURLConnection.HTTP_BAD_REQUEST).entity(out).build();
			}
		} else {
			out = new DefaultReturn();
			out.addErro("'id' é um parâmetro obrigatório");
			response = Response.status(HttpsURLConnection.HTTP_BAD_REQUEST).entity(out).build();
		}
			
		return response;
	}
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON+ ";charset=utf-8")
	public Response getAlunos() {
		try {
			return Response.ok().entity(AlunoServices.findAllSimplified()).build();
		} catch (NoResultException e) {
			return Response.status(HttpsURLConnection.HTTP_NO_CONTENT).build();
		} catch (Exception e) {
			return Response.status(HttpsURLConnection.HTTP_INTERNAL_ERROR).build();
		}
	}

}
