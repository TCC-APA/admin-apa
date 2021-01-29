package br.com.rest.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import br.com.rest.model.dao.AlunoDAO;
import br.com.rest.model.dao.GrupoAlunoDAO;
import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.QuestionarioDAO;
import br.com.rest.model.dto.EstiloDTO;
import br.com.rest.model.dto.QuestaoDTO;
import br.com.rest.model.dto.QuestionarioDTO;
import br.com.rest.model.entity.AlunoEntity;
import br.com.rest.model.entity.EstiloEntity;
import br.com.rest.model.entity.QuestaoEntity;
import br.com.rest.model.entity.QuestionarioEntity;

public class QuestionarioServices {
	
	private static QuestionarioDAO questionarioDao = QuestionarioDAO.getInstance();
	private static GrupoAlunoDAO grupoAlunoDao = GrupoAlunoDAO.getInstance();
	private static AlunoDAO alunoDao = AlunoDAO.getInstance();
	
	
	public static Boolean incluirQuestionario(QuestionarioDTO questionario) {
		QuestionarioEntity questionarioBanco = null;
		
		try {
			 questionarioBanco = questionarioDao.findByNome(questionario.getNome());
		} catch (NoResultException e) {
			System.out.println("Questionario não encontrado no banco, pode ser incluido");
		
		}
		
		if(questionarioBanco != null) {
			return false;
		} else {
			PersistenceManager.getTransaction().begin();
			
			try{
				QuestionarioEntity quest = new QuestionarioEntity();
				questionarioDtoToEntity(questionario, quest);
				questionarioDao.incluir(quest);	
				PersistenceManager.getTransaction().commit();
				return true;
			}catch(Exception e){
				e.printStackTrace();
				PersistenceManager.getTransaction().rollback();
				return false;
			}
		}
	}
	
	public static List<QuestionarioEntity> buscarQuestionariosPorGruposAluno(String matricula){
		List<QuestionarioEntity> questionariosBanco = new ArrayList<QuestionarioEntity>();
		AlunoEntity aluno = null;
		try {
			aluno = alunoDao.buscarByMatricula(matricula);
		} catch(NoResultException e) {
			throw new WebApplicationException(
				      Response.status(Response.Status.NO_CONTENT)
				        .entity("Aluno não existe no banco")
				        .build()
				    );
		}
		
		try {
			questionariosBanco = grupoAlunoDao.findQuestionariosByGruposAluno(aluno);
		} catch(NoResultException e) {
			throw new WebApplicationException(
				      Response.status(Response.Status.NO_CONTENT)
				        .entity("Aluno não está em nenhum grupo de alunos com questionários disponíveis")
				        .build()
				    );
		}
		return questionariosBanco;
	}
	
	public static QuestionarioEntity questionarioDtoToEntity(QuestionarioDTO quest, QuestionarioEntity questEntity) {
		/*if(quest.getEstilosDto() != null && quest.getEstilosDto().size() > 0) {
			EstiloEntity estiloEntity;
			for(EstiloDTO estilo: quest.getEstilosDto()) {
				estiloEntity = new EstiloEntity();
				estiloEntity.setCaracteristicas(estilo.getCaracteristicas());
				estiloEntity.setSugestoes(estilo.getSugestoes());
				estiloEntity.setNome(estilo.getNome());
				estiloEntity.setId(estilo.getId());
				questEntity.addEstilos(estiloEntity);
			}
		}	*/	
		if(quest.getQuestoes() != null && quest.getQuestoes().size() > 0) {
			EstiloEntity estilo = null;
			QuestaoEntity questaoEntity = null;
			for(QuestaoDTO questaoDto: quest.getQuestoes()) {
				questaoEntity = new QuestaoEntity();
				questaoEntity.setIdQuestao(questaoDto.getIdQuestao());
				questaoEntity.setTexto(questaoDto.getTexto());
				if(questaoDto.getEstilo() != null) {
					EstiloDTO estiloDto = questaoDto.getEstilo();
					estilo = new EstiloEntity();
					estilo.setCaracteristicas(estiloDto.getCaracteristicas());
					estilo.setId(estiloDto.getId());
					estilo.setNome(estiloDto.getNome());
					estilo.setSugestoes(estiloDto.getSugestoes());
				} else {
					estilo = null;
				}
				
				questaoEntity.setEstilo(estilo);
				questEntity.addQuestao(questaoEntity);
			}
		}
		if(quest.getValorAlternativas() != null && quest.getValorAlternativas().size() > 0) {
			for(Integer key: quest.getValorAlternativas().keySet()) {
				questEntity.addValorAlternativas(key, quest.getValorAlternativas().get(key));				
			}
		}
		questEntity.setNome(quest.getNome());
		
		return questEntity;		
	}
}
