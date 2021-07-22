package br.com.rest.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		AlunoEntity alunoBanco = null;
		Boolean existeAluno = false;
		if(aluno != null && aluno.getMatricula() != null) {
			alunoBanco = findAlunoByMatricula(aluno.getMatricula());
			if(alunoBanco != null)
				existeAluno = true;
		}
		
		if(existeAluno) {
			ao.addErro("Aluno j� existente no banco, nada foi inclu�do.");
		} else {
			PersistenceManager.getTransaction().begin();
			try{
				AlunoEntity alunoEntity = dtoToEntity(aluno);
				alunoEntity = alunoDao.incluir(alunoEntity);	
				PersistenceManager.getTransaction().commit();
				System.out.println("Aluno "+ aluno.getMatricula() + " incluido");
				ao = entityToDto(alunoEntity);
				ao.setMsg("Aluno inclu�do com sucesso!");
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				ao.addErro("Ocorreu um erro ao inserir o aluno: "+e.getMessage());
			}
		}
		return ao;
	}
	
	public static DefaultReturn alterarAluno(AlunoIn aluno) {
		AlunoOut ao = new AlunoOut();
		AlunoEntity alunoBanco = null;
		Boolean existeAluno = false;
		if(aluno != null && aluno.getMatricula() != null) {
			alunoBanco = findAlunoByMatricula(aluno.getMatricula());
			if(alunoBanco != null)
				existeAluno = true;
		}
		
		if(existeAluno) {
			PersistenceManager.getTransaction().begin();
			try{
				AlunoEntity alunoEntity = dtoToEntity(aluno);
				alunoEntity = alunoDao.alterar(alunoEntity);	
				PersistenceManager.getTransaction().commit();
				System.out.println("Aluno "+ aluno.getMatricula() + " alterado");
				ao = entityToDto(alunoEntity);
				ao.setMsg("Aluno inclu�do com sucesso!");
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				ao.addErro("Ocorreu um erro ao inserir o aluno: "+e.getMessage());
			}
		} else {
			ao.addErro("Aluno n�o existente no banco, nada foi alterado/inserido");
		}
		return ao;
	}
	
	public static AlunoOut findAlunoByMatriculaSenha(String matricula, String senha) {
		AlunoEntity aluno = null;
		AlunoOut ao = null;
		try {
			aluno = alunoDao.findByMatriculaSenha(matricula, senha);
			if(aluno != null) 
				ao = entityToDto(aluno);
			
		} catch(NoResultException e) {
			ao = new AlunoOut();
			ao.addErro("Combina��o de matricula e senha n�o encontrada no banco.");
		}
		return ao;
	}
	
	public static AlunoEntity findAlunoByMatricula(String matricula) {
		AlunoEntity aluno = null;
		try {
			aluno = alunoDao.findByMatricula(matricula);
			System.out.println("Aluno de matricula "+matricula+" encontrado");
			return aluno;
		} catch(NoResultException e) {
			System.out.println("Aluno de matricula "+matricula+" n�o encontrado");
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
			alunoOut.setDataNascimento(alunoEntity.getDataNascimento());
			alunoOut.setMatricula(alunoEntity.getMatricula());
			alunoOut.setNome(alunoEntity.getNome());
		}
		return alunoOut;
	}
	
	public static AlunoEntity dtoToEntity(AlunoIn alunoIn) throws ParseException{
		AlunoEntity alunoEntity = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		if(alunoIn != null) {
			alunoEntity = new AlunoEntity();
			alunoEntity.setGenero(alunoIn.getGenero());
			alunoEntity.setDataNascimento(dateFormat.parse(alunoIn.getDataNascimento()));
			alunoEntity.setMatricula(alunoIn.getMatricula());
			alunoEntity.setNome(alunoIn.getNome());
			alunoEntity.setSenha(alunoIn.getSenha());
		}
		return alunoEntity;
	}
}
