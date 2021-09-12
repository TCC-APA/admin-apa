package br.com.rest.model.dto.filtro.retorno;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.rest.model.dto.EstiloDTO;

@XmlRootElement
public class RetornoColetivoFiltroProfessorOut implements FiltroRetorno{
	private Integer quantidadeTotal;
	private Map<Long, Integer> estilosPredominantesQuantidade;
	private List<EstiloDTO> estilos;
	public Integer getQuantidadeTotal() {
		return quantidadeTotal;
	}
	public void setQuantidadeTotal(Integer quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
	public Map<Long, Integer> getEstilosPredominantesQuantidade() {
		return estilosPredominantesQuantidade;
	}
	public void setEstilosPredominantesQuantidade(Map<Long, Integer> estilosPredominantesQuantidade) {
		this.estilosPredominantesQuantidade = estilosPredominantesQuantidade;
	}
	public List<EstiloDTO> getEstilos() {
		return estilos;
	}
	public void setEstilos(List<EstiloDTO> estilos) {
		this.estilos = estilos;
	}
	
	
}
