package br.com.rest.model.dto;

public class QuestaoDTO {
	
	private Long idQuestao;
	private String texto;
	private EstiloDTO estilo;

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

	public EstiloDTO getEstilo() {
		return estilo;
	}

	public void setEstilo(EstiloDTO estilo) {
		this.estilo = estilo;
	}

	@Override
	public String toString() {
		return "QuestaoDTO [idQuestao=" + idQuestao + ", texto=" + texto + ", estilo=" + estilo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estilo == null) ? 0 : estilo.hashCode());
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
		if (estilo == null) {
			if (other.estilo != null)
				return false;
		} else if (!estilo.equals(other.estilo))
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
