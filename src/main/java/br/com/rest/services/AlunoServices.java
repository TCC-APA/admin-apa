package br.com.rest.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.AlunoDAO;
import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dto.AlunoIn;
import br.com.rest.model.dto.AlunoOut;
import br.com.rest.model.dto.AlunoSimplifiedOut;
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
			ao.addErro("Aluno já existente no banco, nada foi incluído.");
		} else {
			PersistenceManager.getTransaction().begin();
			try{
				AlunoEntity alunoEntity = dtoToEntity(aluno);
				alunoEntity = alunoDao.incluir(alunoEntity);	
				PersistenceManager.getTransaction().commit();
				System.out.println("Aluno "+ aluno.getMatricula() + " incluido");
				ao = entityToDto(alunoEntity);
				ao.setMsg("Aluno incluído com sucesso!");
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				ao.addErro("Ocorreu um erro ao inserir o aluno: "+e.getMessage());
			}
		}
		return ao;
	}
	
	public static void alterarAluno(Long id, AlunoIn aluno) throws Exception{
		PersistenceManager.getTransaction().begin();
		try{
			AlunoEntity alunoEntity = dtoToEntity(aluno);
			alunoEntity.setId(id);
			alunoDao.alterar(alunoEntity);	
			PersistenceManager.getTransaction().commit();
			System.out.println("Aluno "+ aluno.getMatricula() + " alterado");
		}catch(Exception e){
			PersistenceManager.getTransaction().rollback();
			throw e;
		}
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
			ao.addErro("Combinação de matricula e senha não encontrada no banco.");
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
			System.out.println("Aluno de matricula "+matricula+" não encontrado");
			return aluno;
		}
	}
	
	public static AlunoEntity findAlunoById(Long id) {
		AlunoEntity aluno = null;
		try {
			aluno = alunoDao.find(id);
		} catch(NoResultException e) {
			System.out.println("nao foi encontrado aluno com esse id");
		}
		return aluno;
	}
	
	public static List<AlunoEntity> findAll() {
		List<AlunoEntity> alunos = null;
		try {
			alunos = alunoDao.findAll();
		} catch(NoResultException e) {
			System.out.println("Nao ha alunos no banco de dados");
		}
		return alunos;
	}
	
	public static List<AlunoEntity> findAll(String orderBy) {
		List<AlunoEntity> alunos = null;
		try {
			alunos = alunoDao.findAll();
		} catch(NoResultException e) {
			System.out.println("Nao ha alunos no banco de dados");
		}
		return alunos;
	}
	
	public static List<AlunoSimplifiedOut> findAllSimplified() {
		String orderBy = "a.nome, a.matricula";
		List<AlunoEntity> alunos = findAll(orderBy);
		List<AlunoSimplifiedOut> alunosOut = null;
		if(alunos != null && !alunos.isEmpty()) {
			alunosOut = new ArrayList<AlunoSimplifiedOut>();
			for(AlunoEntity a: alunos) 
				alunosOut.add(entityToSimplifiedDto(a));
		} else 
			throw new NoResultException();
		
		return alunosOut;
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
			alunoOut.setSenha(alunoEntity.getSenha());
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
	
	public static AlunoSimplifiedOut entityToSimplifiedDto(AlunoEntity alunoEntity) {
		AlunoSimplifiedOut alunoOut = null;
		if(alunoEntity != null) {
			alunoOut = new AlunoSimplifiedOut();
			alunoOut.setId(alunoEntity.getId());
			alunoOut.setMatricula(alunoEntity.getMatricula());
			alunoOut.setNome(alunoEntity.getNome());
		}
		return alunoOut;
	}
}
