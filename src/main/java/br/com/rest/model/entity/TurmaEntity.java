package br.com.rest.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "Turma")
@Table(name = "TURMA")
@XmlRootElement
public class TurmaEntity extends GrupoAluno implements Serializable {

	private static final long serialVersionUID = 6875426200939558888L;
	
	@Column(unique=true)
	private String codigo;
	
/*	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "REL_TURMA_QUESTIONARIO",
    joinColumns = @JoinColumn(name = "fk_turma"), inverseJoinColumns = @JoinColumn(name = "fk_questionario"))
	private Set<QuestionarioEntity> questionarios;*///TODO ver se funciona sem esse e so com o do grupoaluno
	
	
	@ManyToMany
	@JoinTable(name = "REL_GRUPO_PROFESSOR",
    joinColumns = @JoinColumn(name = "fk_turma"), inverseJoinColumns = @JoinColumn(name = "fk_professor"))
	private Set<ProfessorEntity> professores;
	
	@Column
	private String disciplina;
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Set<ProfessorEntity> getProfessores() {
		return professores;
	}

	public void setProfessores(Set<ProfessorEntity> professor) {
		this.professores = professor;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
	
	

}
