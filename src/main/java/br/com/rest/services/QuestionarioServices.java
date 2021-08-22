package br.com.rest.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.com.rest.model.dao.AlunoDAO;
import br.com.rest.model.dao.GrupoAlunoDAO;
import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.QuestionarioDAO;
import br.com.rest.model.dto.BuscarQuestionariosOut;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.dto.EstiloDTO;
import br.com.rest.model.dto.QuestaoDTO;
import br.com.rest.model.dto.QuestionarioDTO;
import br.com.rest.model.dto.RangePontuacaoClassificacaoDTO;
import br.com.rest.model.dto.ValorAlternativaDTO;
import br.com.rest.model.dto.filtro.BuscarQuestionariosFiltroOut;
import br.com.rest.model.dto.filtro.QuestionarioFiltroOut;
import br.com.rest.model.entity.AlunoEntity;
import br.com.rest.model.entity.EstiloEntity;
import br.com.rest.model.entity.QuestaoEntity;
import br.com.rest.model.entity.QuestionarioEntity;
import br.com.rest.model.entity.RangePontuacaoClassificacao;

public class QuestionarioServices {

	private static QuestionarioDAO questionarioDao = QuestionarioDAO.getInstance();
	private static GrupoAlunoDAO grupoAlunoDao = GrupoAlunoDAO.getInstance();
	private static AlunoDAO alunoDao = AlunoDAO.getInstance();

	public static BuscarQuestionariosFiltroOut buscarQuestionariosFiltroProfessorSimplified()
			throws NoResultException {
		BuscarQuestionariosFiltroOut resposta = new BuscarQuestionariosFiltroOut();
		List<QuestionarioEntity> questionarios = questionarioDao.findAll();
		if (questionarios != null && questionarios.size() > 0)
			entityListToEntityDtoFiltro(resposta, questionarios);
		else
			throw new NoResultException("Nenhum questionario encontrado");

		return resposta;
	}

	private static void entityListToEntityDtoFiltro(BuscarQuestionariosFiltroOut resposta,
			List<QuestionarioEntity> questionarios) {
		for (QuestionarioEntity e : questionarios) {
			QuestionarioFiltroOut out = entityToFiltroOut(e);
			resposta.addQuestionario(out);
		}
	}

	public static QuestionarioFiltroOut entityToFiltroOut(QuestionarioEntity questionarioEntity) {
		QuestionarioFiltroOut questionario = null;
		if (questionarioEntity != null) {
			questionario = new QuestionarioFiltroOut();
			questionario.setNome(questionarioEntity.getNome());
			questionario.setId(questionarioEntity.getIdQuestionario());
		}
		return questionario;
	}

	public static Boolean incluirQuestionario(QuestionarioDTO questionario) {
		QuestionarioEntity questionarioBanco = null;

		try {
			questionarioBanco = questionarioDao.findByNome(questionario.getNome());
		} catch (NoResultException e) {
			System.out.println("Questionario não encontrado no banco, pode ser incluido");

		}

		if (questionarioBanco != null) {
			return false;
		} else {
			PersistenceManager.getTransaction().begin();

			try {
				QuestionarioEntity quest = questionarioDtoToEntity(questionario);
				System.out.println(quest);
				questionarioDao.incluir(quest);
				PersistenceManager.getTransaction().commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				PersistenceManager.getTransaction().rollback();
				return false;
			}
		}
	}

	public static BuscarQuestionariosOut findQuestionariosPorGruposAluno(String matricula) {
		List<QuestionarioEntity> questionariosBanco = new ArrayList<QuestionarioEntity>();
		AlunoEntity aluno = null;
		BuscarQuestionariosOut questionariosOut = new BuscarQuestionariosOut();

		try {
			aluno = alunoDao.findByMatricula(matricula);
		} catch (NoResultException e) {
			/*
			 * throw new WebApplicationException(
			 * Response.status(Response.Status.NO_CONTENT)
			 * .entity("Aluno não existe no banco") .build() );
			 */
			System.out.println("Aluno não existe no banco");
			questionariosOut.addErro("Aluno não existe no banco");
			return questionariosOut;
		}

		try {
			questionariosBanco = grupoAlunoDao.findQuestionariosByGruposAluno(aluno);
		} catch (NoResultException e) {
			/*
			 * throw new WebApplicationException(
			 * Response.status(Response.Status.NO_CONTENT)
			 * .entity("Aluno não está em nenhum grupo de alunos com questionários disponíveis"
			 * ) .build() );
			 */
			System.out.println("Aluno não está em nenhum grupo de alunos com questionários disponíveis");
			questionariosOut.addErro("Aluno não está em nenhum grupo de alunos com questionários disponíveis");
			return questionariosOut;

		}

		List<QuestionarioDTO> listDto = new ArrayList<QuestionarioDTO>();
		for (QuestionarioEntity questEntity : questionariosBanco) {
			QuestionarioDTO questDto = questionarioEntityToDto(questEntity);
			listDto.add(questDto);
		}

		if (listDto.size() > 0)
			questionariosOut.setQuestionarios(listDto);
		else
			questionariosOut.addErro("Aluno não está em nenhum grupo de alunos com questionários disponíveis");

		return questionariosOut;
	}

	public static QuestionarioEntity findQuestionariosPorNome(String nome) {
		QuestionarioEntity questionarioBanco = null;
		try {
			questionarioBanco = questionarioDao.findByNome(nome);
		} catch (NoResultException e) {
			System.out.println("Questionario nao encontrada");
		}

		return questionarioBanco;
	}

	public static QuestionarioEntity findQuestionariosById(Long id) {
		QuestionarioEntity questionarioBanco = null;
		if (id != null) {
			try {
				questionarioBanco = questionarioDao.find(id);
			} catch (NoResultException e) {
				throw new WebApplicationException(Response.status(Response.Status.NO_CONTENT)
						.entity("Questionario de id " + id + " não encontrado.").build());
			}
		}
		return questionarioBanco;
	}

	public static QuestionarioEntity questionarioDtoToEntity(QuestionarioDTO quest) {
		if (quest != null) {
			Map<String, EstiloEntity> estilosEntityIndexados = new HashMap<String, EstiloEntity>();
			QuestionarioEntity questEntity = new QuestionarioEntity();
			if (quest.getEstilosIndexados() != null && quest.getEstilosIndexados().keySet() != null
					&& quest.getEstilosIndexados().keySet().size() > 0) {
				EstiloDTO estiloDto;
				EstiloEntity estiloEntity;
				for (String index : quest.getEstilosIndexados().keySet()) {
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
			
			if (quest.getRanges() != null && quest.getRanges().size() > 0) {
				RangePontuacaoClassificacao rangeEntity = null;
				for (RangePontuacaoClassificacaoDTO rangeDto : quest.getRanges()) {
					rangeEntity = new RangePontuacaoClassificacao();
					rangeEntity.setMinValue(rangeDto.getMinValue());
					rangeEntity.setMaxValue(rangeDto.getMaxValue());
					rangeEntity.setClassificacao(rangeDto.getClassificacao());
					if (rangeDto.getEstiloKey() != null) {
						EstiloEntity estiloQuestao = estilosEntityIndexados.get(rangeDto.getEstiloKey());
						rangeEntity.setEstilo(estiloQuestao);
						try {
							estiloQuestao.addRangeClassificacao(rangeEntity);
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
					}
				}
			}

			if (quest.getValoresAlternativas() != null && quest.getValoresAlternativas().size() > 0) {
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
		if (questEntity != null) {
			quest = new QuestionarioDTO();
			if (questEntity.getEstilos() != null && questEntity.getEstilos().size() > 0) {
				EstiloDTO estilodto;
				EstiloEntity estilo;
				for (int i = 0; i < questEntity.getEstilos().size(); i++) {
					estilodto = new EstiloDTO();
					estilo = questEntity.getEstilos().get(i);
					estilodto.setCaracteristicas(estilo.getCaracteristicas());
					estilodto.setId(estilo.getId());
					estilodto.setNome(estilo.getNome());
					estilodto.setSugestoes(estilo.getSugestoes());
					quest.putEstilosIndexados("" + i, estilodto);
					indiceEstilos.put(estilodto, "" + i);
				}
			}
			if (questEntity.getIdQuestionario() != null)
				quest.setId(questEntity.getIdQuestionario());

			if (questEntity.getNome() != null)
				quest.setNome(questEntity.getNome());

			if (questEntity.getQuestoes() != null && questEntity.getQuestoes().size() > 0) {
				QuestaoDTO questaodto;
				for (QuestaoEntity questao : questEntity.getQuestoes()) {
					questaodto = new QuestaoDTO();
					if (questao.getIdQuestao() != null) {
						questaodto.setIdQuestao(questao.getIdQuestao());
					}

					if (questao.getTexto() != null)
						questaodto.setTexto(questao.getTexto());

					if (questao.getEstilo() != null) {
						// colocar o index do estilo no questionario
						EstiloDTO estilo = EstiloServices.entityToDto(questao.getEstilo());
						String index = indiceEstilos.get(estilo);
						questaodto.setEstiloKey(index);
					}

					quest.addQuestao(questaodto);
				}
			}

			if (questEntity.getValorAlternativas() != null && questEntity.getValorAlternativas().keySet() != null
					&& questEntity.getValorAlternativas().keySet().size() > 0) {
				ValorAlternativaDTO vadto = null;
				for (Integer i : questEntity.getValorAlternativas().keySet()) {
					vadto = new ValorAlternativaDTO();
					vadto.setValor(i);
					vadto.setTextoAlternativa(questEntity.getValorAlternativas().get(i));
					quest.addValoresAlternativas(vadto);
				}
			}
		}
		System.out.println("Json Questionario:");
		System.out.println(new Gson().toJson(quest));
		return quest;
	}
}
