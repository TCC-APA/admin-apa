package br.com.rest.services;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.ProfessorDAO;
import br.com.rest.model.dto.InsereProfessorIn;
import br.com.rest.model.dto.InserirPerfilIn;
import br.com.rest.model.entity.ProfessorEntity;

public class ProfessorServices {
	
	private static ProfessorDAO professorDao = ProfessorDAO.getInstance();
	//private Logger logger = new Logger(), null);
	
	public static Boolean incluirProfessor(InsereProfessorIn professor) {
		ProfessorEntity professorBanco = null;
		try {
			 professorBanco = professorDao.buscarBySiape(professor.getSiape());
		} catch (NoResultException e) {
			System.out.println("Professor não encontrado no banco. Pode ser inserido");
		
		}
		
		if(professorBanco != null) {
			return false;
		} else {
			PersistenceManager.getTransaction().begin();
			ProfessorEntity profEntity = dtoToEntity(professor);
			
			try{
				professorDao.incluir(profEntity);	
				PersistenceManager.getTransaction().commit();
				return true;
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				return false;
			}
		}
	}
	
	public static ProfessorEntity consultarProfessorPorSiape(String siape) {
		ProfessorEntity professor = null;
		try {
			professor = professorDao.buscarBySiape(siape);
			return professor;
		} catch(NoResultException e) {
			return professor;
		}
	}
	
	public static Boolean deleteProfessorPorSiape(String siape) {
		ProfessorEntity professor = null;
		try {
			professor = professorDao.buscarBySiape(siape);
			if(professor != null) {
				PersistenceManager.getTransaction().begin();
				try{
					professorDao.excluirById(professor.getId());
					PersistenceManager.getTransaction().commit();
					return true;
				}catch(Exception e){
					PersistenceManager.getTransaction().rollback();
					return false;
				}
			} else {
				return true;
			}
		} catch(NoResultException e) {
			return true;
		}
	}
	
	public static ProfessorEntity dtoToEntity(InsereProfessorIn professor) {
		ProfessorEntity profEntity = new ProfessorEntity();
		if(professor.getNome() != null) 
			profEntity.setNome(professor.getNome());
		
		if(professor.getSenha() != null)
			profEntity.setSenha(professor.getSenha());
		
		if(professor.getSiape() != null)
			profEntity.setSiape(professor.getSiape());
		
		return profEntity;
	}
}
