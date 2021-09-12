package br.com.rest.services;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.RangePontuacaoClassificacaoDAO;
import br.com.rest.model.dto.RangePontuacaoClassificacaoDTO;
import br.com.rest.model.dto.filtro.retorno.RangePontuacaoClassificacaoFiltro;
import br.com.rest.model.entity.EstiloEntity;
import br.com.rest.model.entity.RangePontuacaoClassificacao;

public class RangePontuacaoClassicifacaoServices {
	
	private static RangePontuacaoClassificacaoDAO rangePontuacaoClassificacaoDao = RangePontuacaoClassificacaoDAO.getInstance();

	public static RangePontuacaoClassificacaoDTO entityToDto(RangePontuacaoClassificacao rangePontuacaoClassificacaoEntity) {
		RangePontuacaoClassificacaoDTO rpc = null;
		if(rangePontuacaoClassificacaoEntity != null) {
			rpc = new RangePontuacaoClassificacaoDTO();
			rpc.setClassificacao(rangePontuacaoClassificacaoEntity.getClassificacao());
			rpc.setMaxValue(rangePontuacaoClassificacaoEntity.getMaxValue());
			rpc.setMinValue(rangePontuacaoClassificacaoEntity.getMinValue());
		}
		return rpc;
	}
	
	public static RangePontuacaoClassificacaoFiltro entityToDtoFiltro(RangePontuacaoClassificacao rangePontuacaoClassificacaoEntity) {
		RangePontuacaoClassificacaoFiltro rpc = null;
		if(rangePontuacaoClassificacaoEntity != null) {
			rpc = new RangePontuacaoClassificacaoFiltro();
			rpc.setClassificacao(rangePontuacaoClassificacaoEntity.getClassificacao());
			rpc.setMaxValue(rangePontuacaoClassificacaoEntity.getMaxValue());
			rpc.setMinValue(rangePontuacaoClassificacaoEntity.getMinValue());
		}
		return rpc;
	}
	
	public static RangePontuacaoClassificacao dtoToEntity(RangePontuacaoClassificacaoDTO rangePontuacaoClassificacaoDto) {
		RangePontuacaoClassificacao rpc = null;
		if(rangePontuacaoClassificacaoDto != null) {
			rpc = new RangePontuacaoClassificacao();
			rpc.setClassificacao(rangePontuacaoClassificacaoDto.getClassificacao());
			rpc.setMaxValue(rangePontuacaoClassificacaoDto.getMaxValue());
			rpc.setMinValue(rangePontuacaoClassificacaoDto.getMinValue());
		}
		return rpc;
	}
	
	public static void associaRangeToEstilo(EstiloEntity estilo, RangePontuacaoClassificacao range) {
		if(range != null && estilo != null) {
			range.setEstilo(estilo);
			rangePontuacaoClassificacaoDao.alterar(range);
		}
	}
}
