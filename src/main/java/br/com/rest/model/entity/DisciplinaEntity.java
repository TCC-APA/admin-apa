/*package br.com.rest.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class DisciplinaEntity {
	
	@Id
	@GeneratedValue
	private Integer idDisciplina;
	
	@Column
	private String codigo;
	
	@Column
	private String nome;
	
	@OneToMany(mappedBy = "disciplina")
	private List<TurmaEntity> turmas;

	@Override
	public String toString() {
		return "DisciplinaEntity [idDisciplina=" + idDisciplina + ", codigo=" + codigo + ", nome=" + nome + ", turmas="
				+ turmas + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((idDisciplina == null) ? 0 : idDisciplina.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((turmas == null) ? 0 : turmas.hashCode());
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
		DisciplinaEntity other = (DisciplinaEntity) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (idDisciplina == null) {
			if (other.idDisciplina != null)
				return false;
		} else if (!idDisciplina.equals(other.idDisciplina))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (turmas == null) {
			if (other.turmas != null)
				return false;
		} else if (!turmas.equals(other.turmas))
			return false;
		return true;
	} 
	
	
	

}
*/