package br.com.rest.model.dao;

import java.util.List;

import br.com.rest.model.entity.TurmaEntity;

public class TurmaDAO extends GenericDAO<TurmaEntity>{
	
	private static TurmaDAO instance = null;
	
	private TurmaDAO() {
		super(TurmaEntity.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static TurmaDAO getInstance() {
		if(instance == null) {
			instance = new TurmaDAO();
		}
		return instance;
	}
	
	public TurmaEntity buscarByCodigo(String codigo) {
		em.clear();
		TurmaEntity turma = (TurmaEntity) em.createQuery(
					"Select a from Turma a Where a.codigo = :codigo")
				.setParameter("codigo", codigo)
				.getSingleResult();
		
		return turma;
				
	}
	
	@SuppressWarnings("unchecked")
	public List<TurmaEntity> buscarByIdProfessor(Integer idProfessor) {
		em.clear();
		List<TurmaEntity> turmas = em.createQuery(
					"SELECT a from Turma a WHERE a.professor.id = :idProfessor")
				.setParameter("idProfessor", idProfessor)
				.getResultList();
		
		return turmas;
	}

}
