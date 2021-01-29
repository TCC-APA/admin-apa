package br.com.rest.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "Questao")
@Table(name = "QUESTAO")
@XmlRootElement
public class QuestaoEntity  implements Serializable {

	private static final long serialVersionUID = 9171755514777773987L;

	@Id
	@GeneratedValue
	private Long idQuestao;
	
	@Column
	private String texto;
	
	@ManyToOne
	@JoinColumn(name = "fk_estilo")
	private EstiloEntity estiloQuestao;
	
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

	public EstiloEntity getEstilo() {
		return estiloQuestao;
	}

	public void setEstilo(EstiloEntity estiloQuestao) {
		this.estiloQuestao = estiloQuestao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idQuestao == null) ? 0 : idQuestao.hashCode());
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
		QuestaoEntity other = (QuestaoEntity) obj;
		if (idQuestao == null) {
			if (other.idQuestao != null)
				return false;
		} else if (!idQuestao.equals(other.idQuestao))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QuestaoEntity [idQuestao=" + idQuestao + ", texto=" + texto + ", estiloQuestao=" + estiloQuestao + "]";
	}
	
	

}
