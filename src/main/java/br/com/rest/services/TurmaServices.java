package br.com.rest.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.TurmaDAO;
import br.com.rest.model.dto.AlunoOut;
import br.com.rest.model.dto.BuscarTurmasOut;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.dto.ProfessorOut;
import br.com.rest.model.dto.QuestionarioDTO;
import br.com.rest.model.dto.TurmaDTO;
import br.com.rest.model.entity.AlunoEntity;
import br.com.rest.model.entity.QuestionarioEntity;
import br.com.rest.model.entity.TurmaEntity;

public class TurmaServices {
	
	private static TurmaDAO turmaDao = TurmaDAO.getInstance();
	
	
	public static Boolean incluirTurma(TurmaEntity turma) {
		TurmaEntity turmaBanco = null;
		try {
			turmaBanco = turmaDao.buscarByCodigo(turma.getCodigo());
		} catch (NoResultException e) {
			System.out.println("Turma não encontrada no banco, pode ser incluído.");
		
		}
		
		if(turmaBanco != null) {
			return false;
		} else {
			PersistenceManager.getTransaction().begin();
			
			try{
				turmaDao.incluir(turma);	
				PersistenceManager.getTransaction().commit();
				return true;
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				return false;
			}
		}
	}
	
	public static TurmaEntity consultarTurmaPorCodigo(String codigo) {
		TurmaEntity turma = null;
		try {
			turma = turmaDao.buscarByCodigo(codigo);
			System.out.println("Turma de codigo "+codigo+" encontrada");
			return turma;
		} catch(NoResultException e) {
			return turma;
		}
	}
	
	public static BuscarTurmasOut buscarTurmasFiltroProfessor(Long idProfessor) {
		BuscarTurmasOut resposta = new BuscarTurmasOut();
		List<TurmaEntity> turmas = null;
		
		if(idProfessor != null) {
			System.out.println("idProfessor passado como parametro, buscando para id: "+ idProfessor);
			turmas = findByIdProfessor(idProfessor);
		} else {
			System.out.println("idProfessor nulo, buscando todas as turmas");
			turmas = findAll();
		}
		
		if(turmas != null) {
			List<TurmaDTO> turmasDto = new ArrayList<TurmaDTO>();
			for(TurmaEntity turmaEntity: turmas) {
				TurmaDTO turmaDto = entityToDto(turmaEntity);
				turmasDto.add(turmaDto);
			}
			resposta.setTurmas(turmasDto);
		} else {
			resposta.setMsg("Nenhuma turma encontrada");
		}
		
		return resposta;
	}
	
	public static List<TurmaEntity> findAll() {
		List<TurmaEntity> turmas = null;
		try {
			turmas = turmaDao.findAll();
		} catch(NoResultException e) {
			System.out.println("Nenhuma turma encontrada");
		}
		return turmas;
	}
	
	public static List<TurmaEntity> findByIdProfessor(Long id) {
		List<TurmaEntity> turmas = null;
		try {
			turmas = turmaDao.buscarByIdProfessor(id);
		} catch(NoResultException e) {
			System.out.println("Nenhuma turma encontrada para professor de id: "+ id);
		}
		return turmas;
	}
	
	public static Set<TurmaEntity> consultarTurmasPorAlunoMatricula(String matricula) {
		Set<TurmaEntity> turmas = null;
		if(matricula != null) {
			try {
				turmas = turmaDao.buscarTurmaByAlunoMatricula(matricula);
						System.out.println("Turmas encontrados do aluno de matrícula "+ matricula);
			} catch(NoResultException e) {
				System.out.println("Turmas não encontrados do aluno de matrícula "+ matricula);
			}
		}
		return turmas;
	}
	
	public static DefaultReturn incluiAlunoTurma(String matricula, String codigoTurma) {
		AlunoEntity aluno = AlunoServices.findAlunoByMatricula(matricula);
		TurmaEntity turma = consultarTurmaPorCodigo(codigoTurma);
		DefaultReturn dr = new DefaultReturn();
		if(aluno != null && turma != null) {
			turma.addAluno(aluno);
			PersistenceManager.getTransaction().begin();
			try{
				turmaDao.alterar(turma);	
				PersistenceManager.getTransaction().commit();
				dr.setMsg("Aluno " + matricula + " inserido com sucesso na turma " + codigoTurma);
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				dr.addErro("erro ao incluir aluno na turma: "+e.getMessage());
			}
		} else {
			dr.addErro("turma ou aluno inválidos.");
		}
		return dr;
	}
	
	public static DefaultReturn incluiAlunoTurmaDefault(String matricula) {
		AlunoEntity aluno = AlunoServices.findAlunoByMatricula(matricula);
		TurmaEntity turma = consultarTurmaPorCodigo("Default");
		if(turma == null) {
			turma = new TurmaEntity();
			turma.setCodigo("Default");
			turma.setDisciplina("Default");
			turma.addQuestionario(QuestionarioServices.findQuestionariosPorNome("CAMEA40"));
			incluirTurma(turma);
		}
		PersistenceManager.getTransaction().begin();
		DefaultReturn dr = new DefaultReturn();
		if(aluno != null && turma != null) {
			turma.addAluno(aluno);
			try{
				turmaDao.alterar(turma);	
				PersistenceManager.getTransaction().commit();
				dr.setMsg("Aluno " + matricula + " inserido com sucesso na turma " + "Default");
				System.out.println("Aluno " + matricula + " inserido com sucesso na turma " + "Default");
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				dr.addErro("erro ao incluir aluno na turma: "+e.getMessage());
				System.out.println("erro ao incluir aluno na turma: "+e.getMessage());
			}
		} else {
			dr.addErro("turma ou aluno inválidos.");
			System.out.println("turma ou aluno inválidos.");
		}
		return dr;
	}
	
	public static TurmaDTO entityToDto(TurmaEntity turmaEntity) {
		TurmaDTO turma = null;
		if(turmaEntity != null) {
			turma = new TurmaDTO();
			turma.setCodigo(turmaEntity.getCodigo());
			turma.setDisciplina(turmaEntity.getDisciplina());
			turma.setId(turmaEntity.getId());
			if(turmaEntity.getProfessor() != null) {
				ProfessorOut prof = ProfessorServices.entityToDto(turmaEntity.getProfessor());
				turma.setProfessor(prof);
			}
			if(turmaEntity.getAlunos() != null && turmaEntity.getAlunos().size() > 0) {
				for(AlunoEntity aluno: turmaEntity.getAlunos()) {
					AlunoOut alunoOut = AlunoServices.entityToDto(aluno);
					turma.addAluno(alunoOut);
				}
			}
			if(turmaEntity.getQuestionarios() != null && turmaEntity.getQuestionarios().size() > 0) {
				for(QuestionarioEntity questionario: turmaEntity.getQuestionarios()) {
					QuestionarioDTO questionarioOut = QuestionarioServices.questionarioEntityToDto(questionario);
					turma.addQuestionario(questionarioOut);
				}
			}
		}
		return turma;
	}
}
