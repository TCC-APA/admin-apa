package br.com.rest.model.dto;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BuscarPerfisFiltroProfessorOut {
	private Integer quantidadeTotal;
	private Map<EstiloDTO, Integer> estilosPredominantesQuantidade;
	private List<EstiloDTO> estilos;
	public Integer getQuantidadeTotal() {
		return quantidadeTotal;
	}
	public void setQuantidadeTotal(Integer quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
	public Map<EstiloDTO, Integer> getEstilosPredominantesQuantidade() {
		return estilosPredominantesQuantidade;
	}
	public void setEstilosPredominantesQuantidade(Map<EstiloDTO, Integer> estilosPredominantesQuantidade) {
		this.estilosPredominantesQuantidade = estilosPredominantesQuantidade;
	}
	public List<EstiloDTO> getEstilos() {
		return estilos;
	}
	public void setEstilos(List<EstiloDTO> estilos) {
		this.estilos = estilos;
	}
	
	
}
