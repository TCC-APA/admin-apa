package br.com.rest.model.dto.filtro.retorno;

public class RangePontuacaoClassificacaoFiltro {
	private Integer minValue;
	private Integer maxValue;
	private String classificacao;
	private Long pontuacao;
	
	public Long getPontuacao() {
		return pontuacao;
	}
	public void setPontuacao(Long pontuacao) {
		this.pontuacao = pontuacao;
	}
	public Integer getMinValue() {
		return minValue;
	}
	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}
	public Integer getMaxValue() {
		return maxValue;
	}
	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}
	public String getClassificacao() {
		return classificacao;
	}
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}
}
