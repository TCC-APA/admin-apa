package br.com.rest.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.AlunoQuestionarioDAO;
import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.TurmaDAO;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.dto.EstiloDTO;
import br.com.rest.model.dto.InserirPerfilIn;
import br.com.rest.model.dto.PerfilAlunoOut;
import br.com.rest.model.dto.RangePontuacaoClassificacaoDTO;
import br.com.rest.model.dto.filtro.retorno.FiltroRetorno;
import br.com.rest.model.dto.filtro.retorno.PerfilAlunoFiltroProfessor;
import br.com.rest.model.dto.filtro.retorno.RangePontuacaoClassificacaoFiltro;
import br.com.rest.model.dto.filtro.retorno.RetornoColetivoFiltroProfessorOut;
import br.com.rest.model.dto.filtro.retorno.RetornoIndividualFiltroProfessorOut;
import br.com.rest.model.entity.AlunoEntity;
import br.com.rest.model.entity.AlunoQuestionarioREL;
import br.com.rest.model.entity.EstiloEntity;
import br.com.rest.model.entity.QuestionarioEntity;
import br.com.rest.model.entity.RangePontuacaoClassificacao;
import br.com.rest.model.entity.TurmaEntity;

public class AlunoQuestionarioRELServices {

	private static AlunoQuestionarioDAO alunoQuestionarioDao = AlunoQuestionarioDAO.getInstance();
	private static TurmaDAO turmaDao = TurmaDAO.getInstance();

	public static FiltroRetorno consultar(Long idQuestionario, String nome, Date startDate, Date endDate,
			Long idTurma) throws Exception {
		List<AlunoQuestionarioREL> resumoEstiloAlunos = null;
		QuestionarioEntity questionario = null;
		TurmaEntity turmaEntity = null;
		if (idTurma != null && idTurma > 0L) {
			try {
				turmaEntity = turmaDao.find(idTurma);
				Set<QuestionarioEntity> listaQuestionarios = turmaEntity.getQuestionarios();
				if (listaQuestionarios != null) {
					questionario = listaQuestionarios.stream().filter(o -> o.getIdQuestionario().equals(idQuestionario))
							.findFirst().get();
				} else {
					System.out.println("Turma de id: " + idTurma + " nao existente no banco");
					return null;
				}
			} catch (NoResultException e) {
				e.printStackTrace();
				return null;
			}
		} else
			questionario = QuestionarioServices.findQuestionariosById(idQuestionario);

		if (questionario == null) {
			System.out.println("Questionario de id: " + idQuestionario + " nao encontrado na turma de id " + idTurma);
			return null;
		}

		try {
			resumoEstiloAlunos = alunoQuestionarioDao.dynamicQueryFiltro(idQuestionario, nome, startDate, endDate,
					turmaEntity);
		} catch (NoResultException e) {
			e.printStackTrace();
			return null;
		}
		try {
			if (resumoEstiloAlunos != null && resumoEstiloAlunos.size() > 0) {
				if (nome != null && nome.length() > 0) {
					return getDadosIndividuais(resumoEstiloAlunos, questionario);
				} else {
					return getDadosColetivo(resumoEstiloAlunos, questionario);
				}
			} else 
				return null;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		}

	}

	private static FiltroRetorno getDadosIndividuais(List<AlunoQuestionarioREL> resumoEstiloAlunos, QuestionarioEntity questionario) {
		List<PerfilAlunoFiltroProfessor> perfilAlunosFiltroProfessor = new ArrayList<PerfilAlunoFiltroProfessor>();
		for (AlunoQuestionarioREL relAlunoQuestionario : resumoEstiloAlunos) {
			Map<EstiloEntity, Long> pontuacaoPorEstilo = relAlunoQuestionario.getPontuacaoPorEstilo();
			List<Long> estilosPredominantes = new ArrayList<>();
			Map<Long, RangePontuacaoClassificacaoFiltro> estilosRangesPontuacao = new HashMap<Long, RangePontuacaoClassificacaoFiltro>();
			int maiorGrauPredominancia = -1;
			if (pontuacaoPorEstilo != null) {
				for (EstiloEntity estilo : relAlunoQuestionario.getPontuacaoPorEstilo().keySet()) {
					Long pontuacaoEstilo = pontuacaoPorEstilo.get(estilo);
					if (estilo.getRangeClassificacao() != null) {
						for (int i = 0; i < estilo.getRangeClassificacao().size(); i++) {
							RangePontuacaoClassificacao rangeEstilo = estilo.getRangeClassificacao().get(i);
							if (pontuacaoEstilo >= rangeEstilo.getMinValue()
									&& pontuacaoEstilo <= rangeEstilo.getMaxValue()) {
								RangePontuacaoClassificacaoFiltro rpc = RangePontuacaoClassicifacaoServices.entityToDtoFiltro(rangeEstilo);
								rpc.setPontuacao(pontuacaoEstilo);
								estilosRangesPontuacao.put(estilo.getId(), rpc);
								if (i > maiorGrauPredominancia) {
									estilosPredominantes.clear();
									estilosPredominantes.add(estilo.getId());
									maiorGrauPredominancia = i;
								} else if (i == maiorGrauPredominancia) {
									estilosPredominantes.add(estilo.getId());
								}
								break;
							}
						}
					}
				}
				AlunoEntity aluno = relAlunoQuestionario.getAluno();
				PerfilAlunoFiltroProfessor perfil = new PerfilAlunoFiltroProfessor();
				perfil.setId(aluno.getId());
				perfil.setNome(aluno.getNome());
				perfil.setMatricula(aluno.getMatricula());
				perfil.setEstilosRangesPontuacao(estilosRangesPontuacao);
				perfil.setEstilosPredominantes(estilosPredominantes);
				
				perfilAlunosFiltroProfessor.add(perfil);
			}
		}
		RetornoIndividualFiltroProfessorOut out = new RetornoIndividualFiltroProfessorOut();
		List<EstiloDTO> estilosDto = new ArrayList<EstiloDTO>();
		for (EstiloEntity e : questionario.getEstilos()) 
			estilosDto.add(EstiloServices.entityToDto(e));
		
		out.setPerfilAlunoFiltroProfessor(perfilAlunosFiltroProfessor);
		out.setEstilos(estilosDto);
		return out;
	}

	private static FiltroRetorno getDadosColetivo(List<AlunoQuestionarioREL> resumoEstiloAlunos,
			QuestionarioEntity questionario) {

		Map<EstiloEntity, Integer> estilosPredominantesQuantidade = new HashMap<>();

		for (EstiloEntity estiloQuestionario : questionario.getEstilos()) {
			estilosPredominantesQuantidade.put(estiloQuestionario, 0);
		}
		for (AlunoQuestionarioREL relAlunoQuestionario : resumoEstiloAlunos) {
			Map<EstiloEntity, Long> pontuacaoPorEstilo = relAlunoQuestionario.getPontuacaoPorEstilo();
			List<EstiloEntity> estilosPredominantes = new ArrayList<>();
			int maiorGrauPredominancia = -1;
			if (pontuacaoPorEstilo != null) {
				for (EstiloEntity estilo : relAlunoQuestionario.getPontuacaoPorEstilo().keySet()) {
					Long pontuacaoEstilo = pontuacaoPorEstilo.get(estilo);

					if (estilo.getRangeClassificacao() != null) {
						for (int i = 0; i < estilo.getRangeClassificacao().size(); i++) {
							RangePontuacaoClassificacao rangeEstilo = estilo.getRangeClassificacao().get(i);
							if (pontuacaoEstilo >= rangeEstilo.getMinValue()
									&& pontuacaoEstilo <= rangeEstilo.getMaxValue()) {
								if (i > maiorGrauPredominancia) {
									estilosPredominantes.clear();
									estilosPredominantes.add(estilo);
									maiorGrauPredominancia = i;
								} else if (i == maiorGrauPredominancia) {
									estilosPredominantes.add(estilo);
								}
								break;
							}
						}
					}
				}
				for (EstiloEntity estiloPredominante : estilosPredominantes) {
					Integer quantidade = estilosPredominantesQuantidade.get(estiloPredominante);
					estilosPredominantesQuantidade.put(estiloPredominante, quantidade + 1);
				}
			}

		}
		RetornoColetivoFiltroProfessorOut out = new RetornoColetivoFiltroProfessorOut();
		List<EstiloDTO> estilosDto = new ArrayList<EstiloDTO>();
		Map<Long, Integer> estilosIdQuantidade = new HashMap<Long, Integer>();
		for (EstiloEntity e : questionario.getEstilos()) {
			EstiloDTO estiloDto = EstiloServices.entityToDto(e);
			estilosDto.add(estiloDto);
			Integer quantidadeEstilo = estilosPredominantesQuantidade.get(e);
			estilosIdQuantidade.put(estiloDto.getId(), quantidadeEstilo);
		}
		out.setQuantidadeTotal(resumoEstiloAlunos.size());
		out.setEstilos(estilosDto);
		out.setEstilosPredominantesQuantidade(estilosIdQuantidade);
		return out;
	}

	public static DefaultReturn inserir(InserirPerfilIn estiloDto) {
		DefaultReturn retornoPadrao = new DefaultReturn();
		AlunoQuestionarioREL estiloAluno = null;
		if(PersistenceManager.getTransaction().isActive()) 
			PersistenceManager.getTransaction().rollback();
		PersistenceManager.getTransaction().begin();

		try {
			estiloAluno = dtoToEntity(estiloDto);
			alunoQuestionarioDao.incluir(estiloAluno);
			PersistenceManager.getTransaction().commit();
			retornoPadrao.setMsg("Pontuacao do aluno inserida com sucesso.");
			return retornoPadrao;
		} catch (Exception e) {
			PersistenceManager.getTransaction().rollback();
			retornoPadrao.addErro(e.getMessage());
			return null;
		}
	}

	public static DefaultReturn consultarPorUltimaData(String matriculaAluno, Long idQuestionario) {
		PerfilAlunoOut perfilAlunoOut = null;
		AlunoQuestionarioREL estiloAluno = null;
		try {
			estiloAluno = alunoQuestionarioDao.findPerfilPorUltimaData(matriculaAluno, idQuestionario);
			if (estiloAluno != null)
				perfilAlunoOut = entityToDto(estiloAluno);
			else {
				perfilAlunoOut = new PerfilAlunoOut();
				perfilAlunoOut.addErro("N�o foi encontrado nenhum registro de perfil do aluno neste questionario.");
			}

		} catch (NoResultException e) {
			perfilAlunoOut = new PerfilAlunoOut();
			perfilAlunoOut.addErro("N�o foi encontrado nenhum registro de perfil do aluno neste questionario.");
		}
		return perfilAlunoOut;
	}

	public static PerfilAlunoOut entityToDto(AlunoQuestionarioREL estiloAluno) { // TODO colocar as pontuacoes por
																					// estilo
		PerfilAlunoOut estiloAlunoDto = new PerfilAlunoOut();

		if (estiloAluno.getAluno() != null) {
			estiloAlunoDto.setIdAluno(estiloAluno.getAluno().getId());
			estiloAlunoDto.setMatriculaAluno(estiloAluno.getAluno().getMatricula());
			estiloAlunoDto.setNomeAluno(estiloAluno.getAluno().getNome());
		}

		if (estiloAluno.getQuestionario() != null) {
			estiloAlunoDto.setIdQuestionario(estiloAluno.getQuestionario().getIdQuestionario());
			estiloAlunoDto.setNomeQuestionario(estiloAluno.getQuestionario().getNome());
			EstiloDTO estiloDto;
			for (EstiloEntity estiloEntity : estiloAluno.getQuestionario().getEstilos()) {
				estiloDto = EstiloServices.entityToDto(estiloEntity);
				estiloAlunoDto.addEstilo(estiloDto);
			}
		}

		estiloAlunoDto.setDataRealizado(estiloAluno.getDataRealizado());
		estiloAlunoDto.setIdPerfil(estiloAluno.getIdPerfil());

		if (estiloAluno.getPontuacaoPorEstilo() != null && estiloAluno.getPontuacaoPorEstilo().keySet() != null
				&& estiloAluno.getPontuacaoPorEstilo().keySet().size() > 0) {
			Map<Long, Long> pontuacaoPorEstiloDto = new HashMap<Long, Long>();
			for (EstiloEntity estilo : estiloAluno.getPontuacaoPorEstilo().keySet()) {
				pontuacaoPorEstiloDto.put(estilo.getId(), estiloAluno.getPontuacaoPorEstilo().get(estilo));
			}
			estiloAlunoDto.setPontuacaoPorEstilo(pontuacaoPorEstiloDto);
		}

		return estiloAlunoDto;
	}

	public static AlunoQuestionarioREL dtoToEntity(InserirPerfilIn estiloAlunoDto) throws IllegalArgumentException { // TODO
																														// colocar
																														// as
																														// pontuacoes
																														// por
																														// estilo
		if (estiloAlunoDto != null
				&& (estiloAlunoDto.getIdAluno() != null || estiloAlunoDto.getMatriculaAluno() != null)
				&& estiloAlunoDto.getIdQuestionario() != null) {
			AlunoQuestionarioREL estiloAlunoRel = new AlunoQuestionarioREL();
			AlunoEntity aluno = null;
			QuestionarioEntity questionario = null;

			if (estiloAlunoDto.getIdAluno() != null) {
				aluno = AlunoServices.findAlunoById(estiloAlunoDto.getIdAluno());
			} else if (estiloAlunoDto.getMatriculaAluno() != null) {
				aluno = AlunoServices.findAlunoByMatricula(estiloAlunoDto.getMatriculaAluno());
			}

			if (aluno == null)
				throw new IllegalArgumentException("Aluno inexistente no banco de dados.");

			estiloAlunoRel.setAluno(aluno);

			if (estiloAlunoDto.getIdQuestionario() != null) {
				questionario = QuestionarioServices.findQuestionariosById(estiloAlunoDto.getIdQuestionario());
			} else {
				return null;
			}

			if (questionario == null)
				throw new IllegalArgumentException("Question�rio inexistente no banco de dados.");

			estiloAlunoRel.setQuestionario(questionario);

			if (estiloAlunoDto.getDataRealizado() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				Date dataFormatada;
				try {
					dataFormatada = dateFormat.parse(estiloAlunoDto.getDataRealizado());
				} catch (ParseException e) {
					throw new IllegalArgumentException("Formato de data errado, deve ser 'dd-MM-yyyy HH:mm:ss'.");
				}
				estiloAlunoRel.setDataRealizado(dataFormatada);
			}

			// TODO acertar a pontuacao por estilo

			if (estiloAlunoDto.getPontuacaoPorEstilo() != null
					&& estiloAlunoDto.getPontuacaoPorEstilo().keySet() != null
					&& estiloAlunoDto.getPontuacaoPorEstilo().keySet().size() > 0) {
				Map<EstiloEntity, Long> pontuacaoPorEstilo = new HashMap<EstiloEntity, Long>();
				for (Long estiloId : estiloAlunoDto.getPontuacaoPorEstilo().keySet()) {
					EstiloEntity estiloEntity = EstiloServices.findEstiloById(estiloId);
					if (estiloEntity != null)
						pontuacaoPorEstilo.put(estiloEntity, estiloAlunoDto.getPontuacaoPorEstilo().get(estiloId));
					else
						throw new IllegalArgumentException("Estilo indexado de id inexistente no banco de dados");
				}
				estiloAlunoRel.setPontuacaoPorEstilo(pontuacaoPorEstilo);
			}

			return estiloAlunoRel;
		} else {
			return null;
		}
	}

}
