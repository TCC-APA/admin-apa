package br.com.rest.model.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QuestionarioDTO extends DefaultReturn{
	
	private Long id;
	private String nome;
	private List<QuestaoDTO> questoes = new ArrayList<QuestaoDTO>();
	private List<ValorAlternativaDTO> valoresAlternativas = new ArrayList<ValorAlternativaDTO>();
	//Estilos com um indice para facilitar a transformacao em JSON
	private Map<String, EstiloDTO> estilosIndexados = new HashMap<String, EstiloDTO>();
	
	public QuestionarioDTO() {
		
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public List<QuestaoDTO> getQuestoes() {
		return questoes;
	}
	
	public void setQuestoes(List<QuestaoDTO> questoes) {
		this.questoes = questoes;
	}
	
	public List<ValorAlternativaDTO> getValoresAlternativas() {
		return valoresAlternativas;
	}
	
	public void setValoresAlternativas(List<ValorAlternativaDTO> valoresAlternativas) {
		this.valoresAlternativas = valoresAlternativas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Map<String, EstiloDTO> getEstilosIndexados() {
		return estilosIndexados;
	}

	public void setEstilosIndexados(Map<String, EstiloDTO> estilosIndexados) {
		this.estilosIndexados = estilosIndexados;
	}
	
	public void putEstilosIndexados(String key, EstiloDTO value) {
		if(this.estilosIndexados == null)
			this.estilosIndexados = new HashMap<String, EstiloDTO>();
		
		this.estilosIndexados.put(key, value);
	}
	
	public void addQuestao(QuestaoDTO questao) {
		if(this.questoes == null)
			this.questoes = new ArrayList<QuestaoDTO>();
		
		this.questoes.add(questao);
	}
	
	public void addValoresAlternativas(ValorAlternativaDTO valorAlternativa) {
		if(this.valoresAlternativas == null)
			this.valoresAlternativas = new ArrayList<ValorAlternativaDTO>();
		
		this.valoresAlternativas.add(valorAlternativa);
	}

	@Override
	public String toString() {
		return "QuestionarioDTO [id=" + id + ", nome=" + nome + ", questoes=" + questoes + ", valoresAlternativas="
				+ valoresAlternativas + ", estilosIndexados=" + estilosIndexados + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estilosIndexados == null) ? 0 : estilosIndexados.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((questoes == null) ? 0 : questoes.hashCode());
		result = prime * result + ((valoresAlternativas == null) ? 0 : valoresAlternativas.hashCode());
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
		QuestionarioDTO other = (QuestionarioDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		
		return true;
	}


	
}
