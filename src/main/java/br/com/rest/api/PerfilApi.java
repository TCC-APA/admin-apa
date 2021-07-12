package br.com.rest.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.rest.model.dto.BuscarPerfilAlunoOut;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.dto.InserirPerfilIn;
import br.com.rest.services.AlunoQuestionarioRELServices;

@Path("/perfil")
public class PerfilApi {

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public BuscarPerfilAlunoOut consultarResumo(@QueryParam(value = "questionario") Long idQuestionario, 
								  @QueryParam(value = "matricula") String matricula, 
			  					  @QueryParam(value = "startDate") String startDate,
			  					  @QueryParam(value = "endDate") String endDate,
								  @QueryParam(value = "turma") String turma) {
		if (idQuestionario == null) {
		    throw new WebApplicationException(
		      Response.status(Response.Status.BAD_REQUEST)
		        .entity("Parâmetro questionário é obrigatório")
		        .build()
		    );
		  }
		
		Date dataInicio = null, dataFim = null;
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

		if(startDate != null) {
			try {
				dataInicio = format.parse(startDate);
			} catch (ParseException e) {
				System.out.println("Erro ao transformar data inicial");
			}
		}
		
		if(endDate != null) {
			try {
				dataFim = format.parse(endDate);
			} catch (ParseException e) {
				System.out.println("Erro ao transformar data final");
			}
		}
		
		BuscarPerfilAlunoOut resumoEstilos = AlunoQuestionarioRELServices.consultar(idQuestionario, matricula, dataInicio, dataFim, turma);
		return resumoEstilos;
	}
	
	@POST
	@Path("/pontuacao")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserirPontuacaoByQuestionario(InserirPerfilIn estiloAlunoDto) {
		System.out.println(estiloAlunoDto);
		Response resp = null;
		DefaultReturn retorno = validaParametroInserirPontuacaoByQuestionario(estiloAlunoDto);
		if(retorno.getErros() != null && retorno.getErros().size() > 0) {
			resp = Response.status(HttpServletResponse.SC_BAD_REQUEST).entity(retorno).build();
		} else {
			resp = Response.ok().entity(AlunoQuestionarioRELServices.inserir(estiloAlunoDto)).build();
		}
		HashMap<Long, Long> mapa= new HashMap<Long, Long>();
		mapa.put(2L, 3L);
		mapa.put(3L, 4L);
		mapa.put(5L, 6L);
		mapa.put(7L, 8L);
		estiloAlunoDto.setPontuacaoPorEstilo(mapa);
		
		System.out.println(new Gson().toJson(estiloAlunoDto));
		return resp;
		}
	
	@GET
	@Path("/pontuacao/ultimaData")
	@Produces(MediaType.APPLICATION_JSON)
	public Response buscarPontuacaoUltimaDataByMatriculaQuestionario(@QueryParam(value = "matricula") String matricula, @QueryParam(value = "idQuestionario") Long idQuestionario) {
		Response resp = null;
		if(matricula == null || matricula.length() == 0 || idQuestionario == null || idQuestionario <= 0L) {
			DefaultReturn retorno = new DefaultReturn();
			retorno.addErro("Id do aluno e do questionário são parâmetros obrigatórios.");
			resp = Response.status(HttpServletResponse.SC_BAD_REQUEST).entity(new Gson().toJson(retorno)).build();
		}
		resp = Response.ok().entity(new Gson().toJson(AlunoQuestionarioRELServices.consultarPorUltimaData(matricula, idQuestionario))).build();
							
		return resp;
	}
	
	private DefaultReturn validaParametroInserirPontuacaoByQuestionario(InserirPerfilIn inserirPerfilIn) {
		DefaultReturn defaultReturn = new DefaultReturn();
		if(inserirPerfilIn.getIdAluno() == null && inserirPerfilIn.getMatriculaAluno() == null) {
			defaultReturn.addErro("Id do aluno e/ou matricula é obrigatório.");
		}
		
		if(inserirPerfilIn.getIdQuestionario() == null) {
			defaultReturn.addErro("Id do questionário é obrigatório.");
		}
		
		if(inserirPerfilIn.getDataRealizado() == null) {
			defaultReturn.addErro("Data que o questionário foi respondido é obrigatória.");
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			try {
				dateFormat.parse(inserirPerfilIn.getDataRealizado());
			} catch (ParseException e) {
				defaultReturn.addErro("Formato da data que foi realizado o questionário é dd-MM-yyyy HH:mm:ss'.");
			}
		}
		
		if(inserirPerfilIn.getPontuacaoPorEstilo() == null) {
			defaultReturn.addErro("Pontuação por estilo é um parâmetro obrigatório");
		} else if(inserirPerfilIn.getPontuacaoPorEstilo().keySet().size() == 0){
			defaultReturn.addErro("Pontuação por estilo não pode ser vazio");
		}
		
		return defaultReturn;
	}
}
