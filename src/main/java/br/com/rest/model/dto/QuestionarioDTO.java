package br.com.rest.model.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class QuestionarioDTO {
	
	private String nome;
	private List<QuestaoDTO> questoes;
	private Map<Integer, String> valorAlternativas = new HashMap<Integer, String>();
	
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
	
	public Map<Integer, String> getValorAlternativas() {
		return valorAlternativas;
	}
	
	public void setValorAlternativas(Map<Integer, String> valorAlternativas) {
		this.valorAlternativas = valorAlternativas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((questoes == null) ? 0 : questoes.hashCode());
		result = prime * result + ((valorAlternativas == null) ? 0 : valorAlternativas.hashCode());
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (questoes == null) {
			if (other.questoes != null)
				return false;
		} else if (!questoes.equals(other.questoes))
			return false;
		if (valorAlternativas == null) {
			if (other.valorAlternativas != null)
				return false;
		} else if (!valorAlternativas.equals(other.valorAlternativas))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QuestionarioDTO [nome=" + nome + ", questoes=" + questoes + ", valorAlternativas=" + valorAlternativas
				+ "]";
	}
	

	
}
