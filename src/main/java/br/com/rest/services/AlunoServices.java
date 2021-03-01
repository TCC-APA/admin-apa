package br.com.rest.services;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.AlunoDAO;
import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dto.AlunoIn;
import br.com.rest.model.dto.AlunoOut;
import br.com.rest.model.dto.DefaultReturn;
import br.com.rest.model.entity.AlunoEntity;

public class AlunoServices {
	
	private static AlunoDAO alunoDao = AlunoDAO.getInstance();
	
	
	public static DefaultReturn incluirAluno(AlunoIn aluno) {
		AlunoOut ao = new AlunoOut();
		Boolean existeAluno = false;
		AlunoEntity alunoBanco = null;
		if(aluno != null && aluno.getMatricula() != null) {
			alunoBanco = findAlunoByMatricula(aluno.getMatricula());
			if(alunoBanco != null)
				existeAluno = true;
		}
		
		if(existeAluno) {
			ao = entityToDto(alunoBanco);
			ao.addErro("Aluno já existente no banco, nada foi incluído.");
		} else {
			AlunoEntity alunoEntity = dtoToEntity(aluno);
			PersistenceManager.getTransaction().begin();
			try{
				alunoDao.incluir(alunoEntity);	
				PersistenceManager.getTransaction().commit();
				ao.setMsg("Aluno incluído com sucesso!");
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				ao.addErro("Ocorreu um erro ao inserir o aluno: "+e.getMessage());
			}
		}
		return ao;
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
	
	public static AlunoOut entityToDto(AlunoEntity alunoEntity) {
		AlunoOut alunoOut = null;
		if(alunoEntity != null) {
			alunoOut = new AlunoOut();
			alunoOut.setId(alunoEntity.getId());
			alunoOut.setGenero(alunoEntity.getGenero());
			alunoOut.setIdade(alunoEntity.getIdade());
			alunoOut.setMatricula(alunoEntity.getMatricula());
			alunoOut.setNome(alunoEntity.getNome());
		}
		return alunoOut;
	}
	
	public static AlunoEntity dtoToEntity(AlunoIn alunoIn) {
		AlunoEntity alunoEntity = null;
		if(alunoIn != null) {
			alunoEntity = new AlunoEntity();
			alunoEntity.setGenero(alunoIn.getGenero());
			alunoEntity.setIdade(alunoIn.getIdade());
			alunoEntity.setMatricula(alunoIn.getMatricula());
			alunoEntity.setNome(alunoIn.getNome());
			alunoEntity.setSenha(alunoIn.getSenha());
		}
		return alunoEntity;
	}
}
