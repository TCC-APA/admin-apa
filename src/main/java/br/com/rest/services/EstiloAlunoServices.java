package br.com.rest.services;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.EstiloAlunoDAO;
import br.com.rest.model.dto.EstiloAlunoDTO;
import br.com.rest.model.dto.EstiloDTO;
import br.com.rest.model.entity.EstiloAlunoREL;
import br.com.rest.model.entity.EstiloEntity;

public class EstiloAlunoServices {

	private static EstiloAlunoDAO estiloAlunoDao = EstiloAlunoDAO.getInstance();

	public static Set<EstiloAlunoDTO> consultar(Long idQuestionario, String matricula, Date startDate, Date endDate, String nivel,
			String turma) {
		Set<EstiloAlunoREL> resumoEstiloAlunos = null;
		Set<EstiloAlunoDTO> resumoEstiloAlunosDTO = null;
		try {
			resumoEstiloAlunos = estiloAlunoDao.dynamicQueryFiltro(idQuestionario, matricula, startDate, endDate, nivel, turma);
			resumoEstiloAlunosDTO = new HashSet<EstiloAlunoDTO>();
			for (EstiloAlunoREL estiloAluno : resumoEstiloAlunos) {
				resumoEstiloAlunosDTO.add(estiloAlunoToDto(estiloAluno));
			}

			return resumoEstiloAlunosDTO;
		} catch (NoResultException e) {
			e.printStackTrace();
			return resumoEstiloAlunosDTO;
		}
	}

	public static EstiloAlunoDTO estiloAlunoToDto(EstiloAlunoREL estiloAluno) { //TODO colocar as pontuacoes por estilo
		EstiloAlunoDTO estiloAlunoDto = new EstiloAlunoDTO();

		if (estiloAluno.getAluno() != null) {
			estiloAlunoDto.setIdAluno(estiloAluno.getAluno().getId());
			estiloAlunoDto.setMatriculaAluno(estiloAluno.getAluno().getMatricula());
			estiloAlunoDto.setNomeAluno(estiloAluno.getAluno().getNome());
		}
		
		if(estiloAluno.getQuestionario() != null) {
			estiloAlunoDto.setIdQuestionario(estiloAluno.getQuestionario().getIdQuestionario());
			estiloAlunoDto.setNomeQuestionario(estiloAluno.getQuestionario().getNome());
			EstiloDTO estiloDto;
			for(EstiloEntity estiloEntity : estiloAluno.getQuestionario().getEstilos()) {
				estiloDto = EstiloServices.entityToDto(estiloEntity);
				estiloAlunoDto.addEstilo(estiloDto);
			}
		}
		
		estiloAlunoDto.setDataRealizado(estiloAluno.getDataRealizado());
		estiloAlunoDto.setIdPerfil(estiloAluno.getIdPerfil());

		return estiloAlunoDto;
	}
}
