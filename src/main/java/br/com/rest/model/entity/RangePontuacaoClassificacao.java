package br.com.rest.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "Range")
@Table(name = "RANGE_PONTUACAO_CLASSIFICACAO")
@XmlRootElement
public class RangePontuacaoClassificacao implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	@NotNull
	private Integer minValue;
	
	@Column
	@NotNull
	private Integer maxValue;
	
	@Column
	@NotNull
	private String classificacao;

	
	@ManyToOne
	@JoinColumn(name = "fk_estilo")
	private EstiloEntity estilo;
	

	public Integer getMinValue() {
		return minValue;
	}

	public Integer getMaxValue() {
		return maxValue;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public EstiloEntity getEstilo() {
		return estilo;
	}

	public void setEstilo(EstiloEntity estilo) {
		this.estilo = estilo;
	}

	public Long getId() {
		return id;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
	}

	public void setMaxValue(Integer maxValue) {
		this.maxValue = maxValue;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
