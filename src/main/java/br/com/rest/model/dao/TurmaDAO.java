package br.com.rest.model.dao;

import java.util.List;
import java.util.Set;

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
					"Select a from Turma a Where UPPER(a.codigo) = :codigo")
				.setParameter("codigo", codigo.toUpperCase().trim())
				.getSingleResult();
		
		return turma;
				
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<TurmaEntity> findAll() {
		em.clear();
		List<TurmaEntity> turmas = (List<TurmaEntity>) em.createQuery(
					"SELECT t FROM Turma t WHERE UPPER(t.codigo) != :codigo")
				.setParameter("codigo", "DEFAULT")
				.getResultList();
		
		return turmas;
				
	}
	
	@SuppressWarnings("unchecked")
	public Set<TurmaEntity> buscarTurmaByAlunoMatricula(String matricula) {
		em.clear();
		Set<TurmaEntity> turmas = (Set<TurmaEntity>) em.createQuery(
					"SELECT t FROM Turma t WHERE "
				  + "(SELECT a FROM Aluno a WHERE UPPER(a.matricula) = :matricula) MEMBER OF t.alunos")
				.setParameter("matricula", matricula.toUpperCase().trim())
				.getResultList();
		
		return turmas;
				
	}
	
	@SuppressWarnings("unchecked")
	public List<TurmaEntity> buscarByIdProfessor(Long idProfessor) {
		em.clear();
		List<TurmaEntity> turmas = em.createQuery(
					"SELECT a from Turma a WHERE a.professores contains (SELECT p from Professor p WHERE p.id = :idProfessor)")
				.setParameter("idProfessor", idProfessor)
				.getResultList();
		
		return turmas;
	}

}
