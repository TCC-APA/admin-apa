package br.com.rest.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "Visitante")
@Table(name = "VISITANTE")
@XmlRootElement
public class VisitanteEntity extends Usuario implements Serializable{
	
	private static final long serialVersionUID = -8372293493127957905L;

	@Column(unique=true)
	private String nivelEscolaridade;
	
	@Override
	public String toString() {
		return "VisitanteEntity [nivelEscolaridade=" + nivelEscolaridade + ", id=" + getId() + ", cpf=" + getNome()
				+ ", idade=" + getIdade() + ", genero=" + getGenero()+"]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfessorEntity other = (ProfessorEntity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}


}
