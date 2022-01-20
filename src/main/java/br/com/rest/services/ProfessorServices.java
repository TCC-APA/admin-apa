package br.com.rest.services;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.ProfessorDAO;
import br.com.rest.model.dto.AlunoOut;
import br.com.rest.model.dto.InsereProfessorIn;
import br.com.rest.model.dto.ProfessorOut;
import br.com.rest.model.entity.AlunoEntity;
import br.com.rest.model.entity.ProfessorEntity;

public class ProfessorServices {

	private static ProfessorDAO professorDao = ProfessorDAO.getInstance();
	// private Logger logger = new Logger(), null);

	public static ProfessorOut incluirProfessor(InsereProfessorIn professorIn) {
		ProfessorOut professorBanco = null;
		ProfessorOut professorOut = new ProfessorOut();

		Boolean existeProfessor = false;
		if (professorIn != null && professorIn.getSiape() != null) {
			professorBanco = consultarProfessorPorSiape(professorIn.getSiape());
			if (professorBanco != null && professorBanco.getId() != null)
				existeProfessor = true;

			if (existeProfessor) {
				professorOut.addErro("Professor j� existente no banco, nada foi inclu�do.");
			} else {
				ProfessorEntity profEntity = dtoToEntity(professorIn);
				if(PersistenceManager.getTransaction().isActive()) 
					PersistenceManager.getTransaction().rollback();
				PersistenceManager.getTransaction().begin();
				try {
					professorDao.incluir(profEntity);
					PersistenceManager.getTransaction().commit();
					professorOut.setMsg("Professor inclu�do com sucesso!");
				} catch (Exception e) {
					PersistenceManager.getTransaction().rollback();
					professorOut.addErro("Ocorreu um erro ao inserir o aluno: " + e.getMessage());
				}
			}
		}
		return professorOut;
	}
	
	public static ProfessorOut findProfessorByMatriculaSenha(String matricula, String senha) {
		ProfessorEntity professor = null;
		ProfessorOut ao = null;
		try {
			professor = professorDao.findByMatriculaSenha(matricula, senha);
			if(professor != null) 
				ao = entityToDto(professor);
			
		} catch(NoResultException e) {
			ao = new ProfessorOut();
			ao.addErro("Combina��o de matricula e senha n�o encontrada no banco.");
		}
		return ao;
	}

	public static ProfessorOut consultarProfessorPorSiape(String siape) {
		ProfessorOut professor = null;
		try {
			professor = entityToDto(professorDao.buscarBySiape(siape));
		} catch (NoResultException e) {
			professor = new ProfessorOut();
			professor.addErro("N�o foi encontrado professor com o Siape/Matricula especificado");
		}
		return professor;
	}

	public static Boolean deleteProfessorPorSiape(String siape) {
		ProfessorEntity professor = null;
		try {
			professor = professorDao.buscarBySiape(siape);
			if (professor != null) {
				if(PersistenceManager.getTransaction().isActive()) 
					PersistenceManager.getTransaction().rollback();
				PersistenceManager.getTransaction().begin();
				try {
					professorDao.excluirById(professor.getId());
					PersistenceManager.getTransaction().commit();
					return true;
				} catch (Exception e) {
					PersistenceManager.getTransaction().rollback();
					return false;
				}
			} else {
				return true;
			}
		} catch (NoResultException e) {
			return true;
		}
	}

	public static ProfessorEntity dtoToEntity(InsereProfessorIn in) {
		ProfessorEntity prof = null;
		if (in != null) {
			prof = new ProfessorEntity();
			prof.setSiape(in.getSiape());
			prof.setNome(in.getNome());
			prof.setSenha(in.getSenha());
		}
		return prof;
	}

	public static ProfessorOut entityToDto(ProfessorEntity in) {
		ProfessorOut prof = null;
		if (in != null) {
			prof = new ProfessorOut();
			prof.setId(in.getId());
			prof.setSiape(in.getSiape());
			prof.setNome(in.getNome());
			prof.setSenha(in.getSenha());
		}
		return prof;
	}
}
