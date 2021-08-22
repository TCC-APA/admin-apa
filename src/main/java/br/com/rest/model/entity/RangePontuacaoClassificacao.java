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

	@Override
	public String toString() {
		return "RangePontuacaoClassificacao [id=" + id + ", minValue=" + minValue + ", maxValue=" + maxValue
				+ ", classificacao=" + classificacao + ", estilo=" + estilo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classificacao == null) ? 0 : classificacao.hashCode());
		result = prime * result + ((estilo == null) ? 0 : estilo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maxValue == null) ? 0 : maxValue.hashCode());
		result = prime * result + ((minValue == null) ? 0 : minValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RangePontuacaoClassificacao other = (RangePontuacaoClassificacao) obj;
		if (classificacao == null) {
			if (other.classificacao != null)
				return false;
		} else if (!classificacao.equals(other.classificacao))
			return false;
		if (estilo == null) {
			if (other.estilo != null)
				return false;
		} else if (!estilo.equals(other.estilo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maxValue == null) {
			if (other.maxValue != null)
				return false;
		} else if (!maxValue.equals(other.maxValue))
			return false;
		if (minValue == null) {
			if (other.minValue != null)
				return false;
		} else if (!minValue.equals(other.minValue))
			return false;
		return true;
	}
	
	
}
