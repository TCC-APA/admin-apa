package br.com.rest.model.dao;

import java.util.List;

import br.com.rest.model.entity.QuestionarioEntity;

public class RELQuestionarioTurmaDAO extends GenericDAO<Object>{
	
	private static RELQuestionarioTurmaDAO instance = null;
	
	private RELQuestionarioTurmaDAO() {
		super(Object.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static RELQuestionarioTurmaDAO getInstance() {
		if(instance == null) {
			instance = new RELQuestionarioTurmaDAO();
		}
		return instance;
	}
	
	
	public List<QuestionarioEntity> buscarByTurma(String codigo) {
		em.clear();
		@SuppressWarnings("unchecked")
		List<QuestionarioEntity> questionarios = (List<QuestionarioEntity>) em.createQuery(
					"Select a from Questionario a"
					+ " inner join a.turmas t"
					+ "  Where t.codigo LIKE :codigo")
				.setParameter("codigo", codigo)
				.getResultList();
		
		return questionarios;
				
	}

}
