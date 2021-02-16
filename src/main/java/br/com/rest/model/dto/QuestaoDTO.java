package br.com.rest.model.dto;

public class QuestaoDTO {
	
	private Long idQuestao;
	private String texto;
	
	//Relacionado ao índice da lista de estilos no QuestionarioDTO
	private String estiloKey;
	
	public Long getIdQuestao() {
		return idQuestao;
	}
	public void setIdQuestao(Long idQuestao) {
		this.idQuestao = idQuestao;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getEstiloKey() {
		return estiloKey;
	}
	public void setEstiloKey(String estiloKey) {
		this.estiloKey = estiloKey;
	}
	@Override
	public String toString() {
		return "QuestaoDTO [idQuestao=" + idQuestao + ", texto=" + texto + ", estiloKey=" + estiloKey + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estiloKey == null) ? 0 : estiloKey.hashCode());
		result = prime * result + ((idQuestao == null) ? 0 : idQuestao.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
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
		QuestaoDTO other = (QuestaoDTO) obj;
		if (estiloKey == null) {
			if (other.estiloKey != null)
				return false;
		} else if (!estiloKey.equals(other.estiloKey))
			return false;
		if (idQuestao == null) {
			if (other.idQuestao != null)
				return false;
		} else if (!idQuestao.equals(other.idQuestao))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		return true;
	}	
}