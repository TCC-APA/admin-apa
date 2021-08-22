package br.com.rest.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

public class GenericDAO<T> {
	
	protected EntityManager em;
	protected Class<T> t;
	
	GenericDAO(Class<T> t, EntityManager em){
		this.t = t;
		this.em = em;
	}
	
	public List<T> findAll(){
		@SuppressWarnings("unchecked")
		List<T> lista = em.createQuery("from " + t.getName()).getResultList();
		
		return lista;
	}
	
	public T find(Long id){
		T t1 = em.find(t, id);
		return t1;
	}
	
	public void excluirById(Long id) {
		T t1 = find(id);
		excluir(t1);
	}
	
	public T incluir(T entidade) {
		em.persist(entidade);
		em.flush();
		return entidade;
	}
	
	public void excluir(T entidade) {
		em.remove(entidade);
		em.flush();
	}
	
	public T alterar(T entidade) {
		em.merge(entidade);
		em.flush();
		return entidade;
	}
	
	public T refresh(T entidade) {
		em.refresh(entidade);
		return entidade;
	}
}
