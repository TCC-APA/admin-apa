package br.com.rest.services;

import java.util.Set;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.TurmaDAO;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.entity.AlunoEntity;
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
}
