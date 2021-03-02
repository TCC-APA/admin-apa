package br.com.rest.services;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.EstiloDAO;
import br.com.rest.model.dto.EstiloDTO;
import br.com.rest.model.entity.EstiloEntity;

public class EstiloServices {
	
	private static EstiloDAO estiloDao = EstiloDAO.getInstance();

	public static EstiloDTO entityToDto(EstiloEntity estiloEntity) {
		EstiloDTO es = null;
		if(estiloEntity != null) {
			es = new EstiloDTO();
			es.setCaracteristicas(estiloEntity.getCaracteristicas());
			es.setId(estiloEntity.getId());
			es.setNome(estiloEntity.getNome());
			es.setSugestoes(estiloEntity.getSugestoes());
		}
		return es;
	}
	
	public static EstiloEntity dtoToEntity(EstiloDTO estiloDto) {
		EstiloEntity es = null;
		if(estiloDto != null) {
			es = new EstiloEntity();
			es.setCaracteristicas(estiloDto.getCaracteristicas());
			es.setId(estiloDto.getId());
			es.setNome(estiloDto.getNome());
			es.setSugestoes(estiloDto.getSugestoes());
		}
		return es;
	}
	
	public static EstiloEntity findEstiloById(Long id) {
		EstiloEntity estilo = null;
		try {
			estilo = estiloDao.find(id);
			return estilo;
		} catch(NoResultException e) {
			return estilo;
		}
	}
}
