package br.com.rest.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@Entity
@XmlRootElement
@XmlSeeAlso({TurmaEntity.class})
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class GrupoAluno  implements Serializable {

	private static final long serialVersionUID = 1004665075414187863L;
	
	@Id
	@GeneratedValue
	private Long id;
		
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "REL_GRUPO_QUESTIONARIO",
    joinColumns = @JoinColumn(name = "fk_turma"), inverseJoinColumns = @JoinColumn(name = "fk_questionario"))
	private Set<QuestionarioEntity> questionarios;
	
	@ManyToMany
	@JoinTable(name = "REL_GRUPO_ALUNO",
		joinColumns = { @JoinColumn(name = "fk_turma") },
		inverseJoinColumns = { @JoinColumn(name = "fk_aluno") })
	private List<AlunoEntity> alunos;
	

	public Long getId() {
		return id;
	}

	public void setId(Long idTurma) {
		this.id = idTurma;
	}

	public Set<QuestionarioEntity> getQuestionarios() {
		return questionarios;
	}

	public void setQuestionarios(Set<QuestionarioEntity> questionarios) {
		this.questionarios = questionarios;
	}


	public List<AlunoEntity> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<AlunoEntity> alunos) {
		this.alunos = alunos;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		GrupoAluno other = (GrupoAluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
}