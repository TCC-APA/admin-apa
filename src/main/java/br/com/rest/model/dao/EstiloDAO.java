package br.com.rest.model.dao;

import br.com.rest.model.entity.EstiloEntity;

public class EstiloDAO extends GenericDAO<EstiloEntity>{
	
	private static EstiloDAO instance = null;
	
	private EstiloDAO() {
		super(EstiloEntity.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static EstiloDAO getInstance() {
		if(instance == null) {
			instance = new EstiloDAO();
		}
		return instance;
	}
}