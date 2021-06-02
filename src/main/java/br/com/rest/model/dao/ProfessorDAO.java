package br.com.rest.model.dao;

import br.com.rest.model.entity.AlunoEntity;
import br.com.rest.model.entity.ProfessorEntity;

public class ProfessorDAO extends GenericDAO<ProfessorEntity>{
	
	private static ProfessorDAO instance = null;
	
	private ProfessorDAO() {
		super(ProfessorEntity.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static ProfessorDAO getInstance() {
		if(instance == null) {
			instance = new ProfessorDAO();
		}
		return instance;
	}
	
	public ProfessorEntity buscarBySiape(String siape) {
		em.clear();
		ProfessorEntity professor = (ProfessorEntity) em.createQuery(
					"Select a from Professor a Where UPPER(a.siape) = UPPER(:siape)")
				.setParameter("siape", siape)
				.getSingleResult();
		
		return professor;
				
	}
	
	public ProfessorEntity findByMatriculaSenha(String matricula, String senha) {
		em.clear();
		ProfessorEntity professor = (ProfessorEntity) em.createQuery(
					"SELECT a from Professor a WHERE a.siape = :matricula AND a.senha = :senha")
				.setParameter("matricula", matricula)
				.setParameter("senha", senha)
				.getSingleResult();
		
		return professor;
	}

}
