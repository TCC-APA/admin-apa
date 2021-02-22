package br.com.rest.services;

import java.util.List;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.AlunoDAO;
import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.entity.AlunoEntity;

public class AlunoServices {
	
	private static AlunoDAO alunoDao = AlunoDAO.getInstance();
	
	
	public static Boolean incluirAluno(AlunoEntity aluno) {
		List<AlunoEntity> listaAlunos = alunoDao.findAll();
		Boolean existeAluno = false;
		for(AlunoEntity alunoBanco: listaAlunos) {
			if(alunoBanco.getMatricula().equals(aluno.getMatricula())) {
				existeAluno = true;
			}
		}
		
		if(existeAluno) {
			return false;
		} else {
			PersistenceManager.getTransaction().begin();
			
			try{
				alunoDao.incluir(aluno);	
				PersistenceManager.getTransaction().commit();
				return true;
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				return false;
			}
		}
	}
	
	public static AlunoEntity findAlunoByMatriculaSenha(String matricula, String senha) {
		AlunoEntity aluno = null;
		try {
			aluno = alunoDao.findByMatriculaSenha(matricula, senha);
			return aluno;
		} catch(NoResultException e) {
			return aluno;
		}
	}
	
	public static AlunoEntity findAlunoByMatricula(String matricula) {
		AlunoEntity aluno = null;
		try {
			aluno = alunoDao.findByMatricula(matricula);
			return aluno;
		} catch(NoResultException e) {
			return aluno;
		}
	}
	
	public static AlunoEntity findAlunoById(Long id) {
		AlunoEntity aluno = null;
		try {
			aluno = alunoDao.find(id);
			return aluno;
		} catch(NoResultException e) {
			return aluno;
		}
	}
}
