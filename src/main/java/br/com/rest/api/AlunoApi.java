package br.com.rest.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.rest.model.dto.AlunoIn;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.dto.LoginDTO;
import br.com.rest.services.AlunoServices;
import br.com.rest.services.TurmaServices;

@Path("/aluno")
public class AlunoApi {

	@POST
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public DefaultReturn inserirAluno(AlunoIn aluno, @QueryParam(value = "turmaDefault") Boolean turmaDefault) {
		DefaultReturn dr = AlunoServices.incluirAluno(aluno);
		if((dr.getErros() == null || dr.getErros().size() == 0) && turmaDefault) {
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

}
