package br.com.rest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import br.com.rest.model.dao.AlunoDAO;
import br.com.rest.model.dao.GrupoAlunoDAO;
import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.QuestionarioDAO;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.dto.EstiloDTO;
import br.com.rest.model.dto.QuestaoDTO;
import br.com.rest.model.dto.QuestionarioDTO;
import br.com.rest.model.dto.ValorAlternativaDTO;
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
				QuestionarioEntity quest = questionarioDtoToEntity(questionario);
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
	
	public static List<QuestionarioDTO> findQuestionariosPorGruposAluno(String matricula){
		List<QuestionarioEntity> questionariosBanco = new ArrayList<QuestionarioEntity>();
		AlunoEntity aluno = null;
		try {
			aluno = alunoDao.findByMatricula(matricula);
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
		
		List<QuestionarioDTO> listDto = new ArrayList<QuestionarioDTO>();
		for(QuestionarioEntity questEntity : questionariosBanco) {
			QuestionarioDTO questDto = questionarioEntityToDto(questEntity);
			listDto.add(questDto);
		}
		
		return listDto;
	}
	
	public static QuestionarioEntity findQuestionariosPorNome(String nome){
		QuestionarioEntity questionarioBanco = new QuestionarioEntity();
		try {
			questionarioBanco = questionarioDao.findByNome(nome);
		} catch(NoResultException e) {
			System.out.println("Turma nao encontrada");
		}
				
		return questionarioBanco;
	}
	
	public static QuestionarioEntity findQuestionariosById(Long id){
		QuestionarioEntity questionarioBanco = null;
		if(id != null) {
			try {
				questionarioBanco = questionarioDao.find(id);
			} catch(NoResultException e) {
				throw new WebApplicationException(
						Response.status(Response.Status.NO_CONTENT)
						.entity("Questionario de id "+ id + " não encontrado.")
						.build()
						);
			}			
		}
		return questionarioBanco;
	}
	
	public static QuestionarioEntity questionarioDtoToEntity(QuestionarioDTO quest) {
		if (quest != null) {
			Map<String, EstiloEntity> estilosEntityIndexados = new HashMap<String, EstiloEntity>();
			QuestionarioEntity questEntity = null;
			if (quest.getEstilosIndexados() != null && quest.getEstilosIndexados().keySet() != null
					&& quest.getEstilosIndexados().keySet().size() > 0) {
				EstiloDTO estiloDto;
				EstiloEntity estiloEntity;
				questEntity = new QuestionarioEntity();
				for (String index: quest.getEstilosIndexados().keySet()) {
					estiloDto = quest.getEstilosIndexados().get(index);
					estiloEntity = new EstiloEntity();
					estiloEntity.setCaracteristicas(estiloDto.getCaracteristicas());
					estiloEntity.setSugestoes(estiloDto.getSugestoes());
					estiloEntity.setNome(estiloDto.getNome());
					estiloEntity.setId(estiloDto.getId());
					questEntity.addEstilos(estiloEntity);

					estilosEntityIndexados.put(index, estiloEntity);
				}
			}

			if (quest.getQuestoes() != null && quest.getQuestoes().size() > 0) {
				if (questEntity == null)
					questEntity = new QuestionarioEntity();

				QuestaoEntity questaoEntity = null;
				for (QuestaoDTO questaoDto : quest.getQuestoes()) {
					questaoEntity = new QuestaoEntity();
					questaoEntity.setIdQuestao(questaoDto.getIdQuestao());
					questaoEntity.setTexto(questaoDto.getTexto());
					if (questaoDto.getEstiloKey() != null) {
						EstiloEntity estiloQuestao = estilosEntityIndexados.get(questaoDto.getEstiloKey());
						questaoEntity.setEstilo(estiloQuestao);
					}
					questEntity.addQuestao(questaoEntity);
				}
			}

			if (quest.getValoresAlternativas() != null && quest.getValoresAlternativas().size() > 0) {
				if (questEntity == null)
					questEntity = new QuestionarioEntity();

				for (ValorAlternativaDTO valorDto : quest.getValoresAlternativas())
					questEntity.addValorAlternativas(valorDto.getValor(), valorDto.getTextoAlternativa());
			}

			questEntity.setNome(quest.getNome());
			return questEntity;

		} else
			return null;
	}
	
	public static QuestionarioDTO questionarioEntityToDto(QuestionarioEntity questEntity) {
		QuestionarioDTO quest = null;
		Map<EstiloDTO, String> indiceEstilos = new HashMap<EstiloDTO, String>();
		if(questEntity != null){
			quest = new QuestionarioDTO();
			if(questEntity.getEstilos() != null && questEntity.getEstilos().size() > 0) {
				EstiloDTO estilodto;
				EstiloEntity estilo;
				for(int i = 0; i < questEntity.getEstilos().size(); i++) {
					estilodto = new EstiloDTO();
					estilo = questEntity.getEstilos().get(i);
					estilodto.setCaracteristicas(estilo.getCaracteristicas());
					estilodto.setId(estilo.getId());
					estilodto.setNome(estilo.getNome());
					estilodto.setSugestoes(estilo.getSugestoes());
					quest.putEstilosIndexados(""+i, estilodto);
					indiceEstilos.put(estilodto, ""+i);
				}
			}
			if(questEntity.getIdQuestionario() != null)
				quest.setId(questEntity.getIdQuestionario());
			
			if(questEntity.getNome() != null)
				quest.setNome(questEntity.getNome());
			
			if(questEntity.getQuestoes() != null && questEntity.getQuestoes().size() > 0) {
				QuestaoDTO questaodto;
				for(QuestaoEntity questao : questEntity.getQuestoes()) {
					questaodto = new QuestaoDTO();
					if(questao.getIdQuestao() != null) {
						questaodto.setIdQuestao(questao.getIdQuestao());
					}
					
					if(questao.getTexto() != null)
						questaodto.setTexto(questao.getTexto());
					
					if(questao.getEstilo() != null) {
						//colocar o index do estilo no questionario
						EstiloDTO estilo = EstiloServices.entityToDto(questao.getEstilo());
						String index = indiceEstilos.get(estilo);
						questaodto.setEstiloKey(index);
					}
						
					quest.addQuestao(questaodto);
				}
			}
			
			if(questEntity.getValorAlternativas() != null && questEntity.getValorAlternativas().keySet() != null && questEntity.getValorAlternativas().keySet().size() > 0) {
				ValorAlternativaDTO vadto = null;
				for(Integer i : questEntity.getValorAlternativas().keySet()) {
					vadto = new ValorAlternativaDTO();
					vadto.setValor(i);
					vadto.setTextoAlternativa(questEntity.getValorAlternativas().get(i));
					quest.addValoresAlternativas(vadto);
				}
			}
		}
		return quest;
	}
}
