package br.com.rest.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PersistenceManager {
	
	private static EntityManagerFactory factory =
			Persistence.createEntityManagerFactory("perfilaprendizadoPU");
	private static EntityManager em = factory.createEntityManager();
	
	static EntityManager getEntityManager(){
		return em;
	}
	

	public static <T> GenericDAO<T> createGenericDAO(Class<T> t) {
		return new GenericDAO<T>(t, em);
	}
	
	public static EntityTransaction getTransaction(){
		return em.getTransaction();
	}

}

