package br.com.rest.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.TurmaDAO;
import br.com.rest.model.dto.AlunoOut;
import br.com.rest.model.dto.BuscarTurmasOut;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.dto.ProfessorOut;
import br.com.rest.model.dto.QuestionarioDTO;
import br.com.rest.model.dto.TurmaDTO;
import br.com.rest.model.dto.filtro.BuscarTurmasFiltroOut;
import br.com.rest.model.dto.filtro.TurmaFiltroOut;
import br.com.rest.model.entity.AlunoEntity;
import br.com.rest.model.entity.ProfessorEntity;
import br.com.rest.model.entity.QuestionarioEntity;
import br.com.rest.model.entity.TurmaEntity;

public class TurmaServices {
	
	private static TurmaDAO turmaDao = TurmaDAO.getInstance();
	
	
	public static Boolean incluirTurma(TurmaEntity turma) {
		TurmaEntity turmaBanco = null;
		try {
			turmaBanco = turmaDao.buscarByCodigo(turma.getCodigo());
		} catch (NoResultException e) {
			System.out.println("Turma n�o encontrada no banco, pode ser inclu�do.");
		
		}
		
		if(turmaBanco != null) {
			return false;
		} else {
			if(PersistenceManager.getTransaction().isActive()) 
				PersistenceManager.getTransaction().rollback();
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
	

	public static BuscarTurmasFiltroOut buscarTurmasFiltroProfessorSimplified(Long idProfessor) throws NoResultException{
		BuscarTurmasFiltroOut resposta = new BuscarTurmasFiltroOut();
		List<TurmaEntity> turmas = new ArrayList<TurmaEntity>();
		getTurmasByIdProfParameter(idProfessor, turmas);
		
		if(turmas != null && turmas.size() > 0)
			turmasListToSimplifiedDto(resposta, turmas);
		else 
			throw new NoResultException("Nenhuma turma encontrada");
		
		return resposta;
	}

	private static void turmasListToSimplifiedDto(BuscarTurmasFiltroOut resposta, List<TurmaEntity> turmas) {
		List<TurmaFiltroOut> turmasDto = new ArrayList<TurmaFiltroOut>();
		for(TurmaEntity turmaEntity: turmas) {
			TurmaFiltroOut turmaDto = entityToFiltroOut(turmaEntity);
			turmasDto.add(turmaDto);
		}
		resposta.setTurmas(turmasDto);
	}
	
	private static void getTurmasByIdProfParameter(Long idProfessor, List<TurmaEntity> turmas) {		
		List<TurmaEntity> turmasDb = null;
		if(idProfessor != null) {
			System.out.println("idProfessor passado como parametro, buscando para id: "+ idProfessor);
			turmasDb = findByIdProfessor(idProfessor);
		} else {
			System.out.println("idProfessor nulo, buscando todas as turmas");
			turmasDb = findAll();
		}
		turmas.addAll(turmasDb);
	}
	
	public static List<TurmaEntity> findAll() throws NoResultException{
		return turmaDao.findAll();
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
						System.out.println("Turmas encontrados do aluno de matr�cula "+ matricula);
			} catch(NoResultException e) {
				System.out.println("Turmas n�o encontrados do aluno de matr�cula "+ matricula);
			}
		}
		return turmas;
	}
	
	public static void incluiAlunoTurma(String matricula, String codigoTurma) {
		AlunoEntity aluno = AlunoServices.findAlunoByMatricula(matricula);
		TurmaEntity turma = consultarTurmaPorCodigo(codigoTurma);
		if(aluno != null && turma != null) {
			turma.addAluno(aluno);
			if(PersistenceManager.getTransaction().isActive()) 
				PersistenceManager.getTransaction().rollback();
			PersistenceManager.getTransaction().begin();
			try{
				turmaDao.alterar(turma);	
				PersistenceManager.getTransaction().commit();
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				throw e;
			}
		} else {
			System.out.println("Aluno ou turma n�o encontrada");
			throw new IllegalStateException("Aluno ou turma n�o encontrada");
		}
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
		if(PersistenceManager.getTransaction().isActive()) 
			PersistenceManager.getTransaction().rollback();
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
			dr.addErro("turma ou aluno inv�lidos.");
			System.out.println("turma ou aluno inv�lidos.");
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
			if(turmaEntity.getProfessores() != null && turmaEntity.getProfessores().size() > 0) {
				Set<ProfessorOut> poutSet = new HashSet<ProfessorOut>();
				for(ProfessorEntity p: turmaEntity.getProfessores()) {					
					ProfessorOut prof = ProfessorServices.entityToDto(p);
					poutSet.add(prof);
				}
				turma.setProfessores(poutSet);
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
	
	public static TurmaFiltroOut entityToFiltroOut(TurmaEntity turmaEntity) {
		TurmaFiltroOut turma = null;
		if(turmaEntity != null) {
			turma = new TurmaFiltroOut();
			turma.setCodigo(turmaEntity.getCodigo());
			turma.setDisciplina(turmaEntity.getDisciplina());
			turma.setId(turmaEntity.getId());
		}
		return turma;
	}
}
