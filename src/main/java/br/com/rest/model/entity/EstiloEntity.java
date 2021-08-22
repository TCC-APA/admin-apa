package br.com.rest.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "Estilo")
@Table(name = "ESTILO")
@XmlRootElement
public class EstiloEntity implements Serializable{

	private static final long serialVersionUID = 4004444728354480995L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String nome;
	
	@Column
	private String caracteristicas;
	
	@Column
	private String sugestoes;
	
	@Column
	private String comoMelhorar;
		
	@OrderBy("minRange")
	@OneToMany(cascade = {CascadeType.ALL})
	@JoinColumn(name = "fk_questionario")
	private List<RangePontuacaoClassificacao> rangeClassificacao;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getSugestoes() {
		return sugestoes;
	}

	public void setSugestoes(String sugestoes) {
		this.sugestoes = sugestoes;
	}	
	
	public String getComoMelhorar() {
		return comoMelhorar;
	}

	public void setComoMelhorar(String comoMelhorar) {
		this.comoMelhorar = comoMelhorar;
	}

	public List<RangePontuacaoClassificacao> getRangeClassificacao() {
		return rangeClassificacao;
	}

	public void setRangeClassificacao(List<RangePontuacaoClassificacao> rangeClassificacao) throws Exception {
		if(rangeClassificacao != null && rangeClassificacao.size() > 0) {
			for(RangePontuacaoClassificacao r: rangeClassificacao) {
				if(r.getMinValue() == null || r.getMaxValue() == null || r.getMinValue() >= r.getMaxValue()) {
					throw new Exception("Ranges de classificacao invalidos. "
							+ "Verifique se todo minimo possui um maximo e que todos os minimos sao menores que os maximos");
				}
			}
		}
		this.rangeClassificacao = rangeClassificacao;
	}
	
	public void addRangeClassificacao(RangePontuacaoClassificacao rangePontuacaoClassificacao) throws Exception {
		if(rangePontuacaoClassificacao == null
		|| rangePontuacaoClassificacao.getMinValue() == null || rangePontuacaoClassificacao.getMaxValue() == null
		|| rangePontuacaoClassificacao.getMinValue() >= rangePontuacaoClassificacao.getMaxValue()) 
			throw new Exception("Valor minimo nao pode ser maior ou igual o valor maximo");
		
		Integer minValue = rangePontuacaoClassificacao.getMinValue();
		Integer maxValue = rangePontuacaoClassificacao.getMaxValue();
		
		if(this.rangeClassificacao == null)
			this.rangeClassificacao = new ArrayList<RangePontuacaoClassificacao>();
		else {
			for(RangePontuacaoClassificacao r: this.rangeClassificacao) {
				if((minValue >= r.getMinValue() && minValue <= r.getMaxValue()) 
				 || maxValue >= r.getMinValue() && maxValue <= r.getMaxValue())
					throw new Exception("Range infringe valores de outras Ranges já existentes");
			}
		}
		
		this.rangeClassificacao.add(rangePontuacaoClassificacao);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caracteristicas == null) ? 0 : caracteristicas.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((sugestoes == null) ? 0 : sugestoes.hashCode());
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
		EstiloEntity other = (EstiloEntity) obj;
		if (caracteristicas == null) {
			if (other.caracteristicas != null)
				return false;
		} else if (!caracteristicas.equals(other.caracteristicas))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (sugestoes == null) {
			if (other.sugestoes != null)
				return false;
		} else if (!sugestoes.equals(other.sugestoes))
			return false;
		return true;
	}
	

}
