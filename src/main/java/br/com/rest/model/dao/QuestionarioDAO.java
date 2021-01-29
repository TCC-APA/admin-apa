package br.com.rest.model.dao;

import br.com.rest.model.entity.QuestionarioEntity;

public class QuestionarioDAO extends GenericDAO<QuestionarioEntity>{
	
	private static QuestionarioDAO instance = null;
	
	private QuestionarioDAO() {
		super(QuestionarioEntity.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static QuestionarioDAO getInstance() {
		if(instance == null) {
			instance = new QuestionarioDAO();
		}
		return instance;
	}

	public QuestionarioEntity findByNome(String nome) {
		em.clear();
		QuestionarioEntity quest = (QuestionarioEntity) em.createQuery(
					"Select a from Questionario a Where UPPER(a.nome) = :nome")
				.setParameter("nome", nome.toUpperCase())
				.getSingleResult();
		
		return quest;
				
	}

}
