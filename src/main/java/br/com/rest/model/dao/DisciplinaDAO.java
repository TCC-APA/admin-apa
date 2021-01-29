/*package br.com.rest.model.dao;

import br.com.rest.model.entity.DisciplinaEntity;

public class DisciplinaDAO extends GenericDAO<DisciplinaEntity>{
	
	private static DisciplinaDAO instance = null;
	
	private DisciplinaDAO() {
		super(DisciplinaEntity.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static DisciplinaDAO getInstance() {
		if(instance == null) {
			instance = new DisciplinaDAO();
		}
		return instance;
	}
	
	public DisciplinaEntity buscarByCodigo(String codigo) {
		em.clear();
		DisciplinaEntity disciplina = (DisciplinaEntity) em.createQuery(
					"Select d from DisciplinaEntity d Where d.codigo LIKE :codigo")
				.setParameter("codigo", codigo)
				.getSingleResult();
		
		return disciplina;
				
	}
	
	public DisciplinaEntity buscarByMatriculaSenha(String matricula, String senha) {
		em.clear();
		DisciplinaEntity aluno = (DisciplinaEntity) em.createQuery(
					"SELECT d from DisciplinaEntity d WHERE d.matricula = :matricula AND d.senha = :senha")
				.setParameter("matricula", matricula)
				.setParameter("senha", senha)
				.getSingleResult();
		
		return aluno;
	}

}*/
