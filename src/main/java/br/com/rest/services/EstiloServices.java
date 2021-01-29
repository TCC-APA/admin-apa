package br.com.rest.services;

import br.com.rest.model.dto.EstiloDTO;
import br.com.rest.model.entity.EstiloEntity;

public class EstiloServices {

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
}
