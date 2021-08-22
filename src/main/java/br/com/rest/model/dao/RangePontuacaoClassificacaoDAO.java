package br.com.rest.model.dao;

import javax.persistence.PersistenceException;

import br.com.rest.model.entity.RangePontuacaoClassificacao;

public class RangePontuacaoClassificacaoDAO extends GenericDAO<RangePontuacaoClassificacao>{
	
	private static RangePontuacaoClassificacaoDAO instance = null;
	
	private RangePontuacaoClassificacaoDAO() {
		super(RangePontuacaoClassificacao.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static RangePontuacaoClassificacaoDAO getInstance() {
		if(instance == null) {
			instance = new RangePontuacaoClassificacaoDAO();
		}
		return instance;
	}
	
	@Override
	public RangePontuacaoClassificacao incluir(RangePontuacaoClassificacao entidade) {
		if(entidade.getMinValue()== null || entidade.getMaxValue() == null || entidade.getClassificacao() == null || entidade.getMinValue() >= entidade.getMaxValue())
			throw new PersistenceException("Valores inválidos. Nenhum valor pode ser nulo, e valor minimo deve ser maior que valor maximo");
		
		return super.incluir(entidade);
	}
}
