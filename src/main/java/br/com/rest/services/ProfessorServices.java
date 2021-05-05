package br.com.rest.services;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.ProfessorDAO;
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
				professorOut.addErro("Professor já existente no banco, nada foi incluído.");
			} else {
				ProfessorEntity profEntity = dtoToEntity(professorIn);
				PersistenceManager.getTransaction().begin();
				try {
					professorDao.incluir(profEntity);
					PersistenceManager.getTransaction().commit();
					professorOut.setMsg("Professor incluído com sucesso!");
				} catch (Exception e) {
					PersistenceManager.getTransaction().rollback();
					professorOut.addErro("Ocorreu um erro ao inserir o aluno: " + e.getMessage());
				}
			}
		}
		return professorOut;
	}

	public static ProfessorOut consultarProfessorPorSiape(String siape) {
		ProfessorOut professor = null;
		try {
			professor = entityToDto(professorDao.buscarBySiape(siape));
		} catch (NoResultException e) {
			professor = new ProfessorOut();
			professor.addErro("Não foi encontrado professor com o Siape/Matricula especificado");
		}
		return professor;
	}

	public static Boolean deleteProfessorPorSiape(String siape) {
		ProfessorEntity professor = null;
		try {
			professor = professorDao.buscarBySiape(siape);
			if (professor != null) {
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
