package br.com.rest.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TurmaDTO extends DefaultReturn{
	
	private Long id;
	private List<QuestionarioDTO> questionarios;
	private List<AlunoOut> alunos;
	private String codigo;
	private ProfessorOut professor;
	private String disciplina;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<QuestionarioDTO> getQuestionarios() {
		return questionarios;
	}
	public void setQuestionarios(List<QuestionarioDTO> questionarios) {
		this.questionarios = questionarios;
	}
	public List<AlunoOut> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<AlunoOut> alunos) {
		this.alunos = alunos;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public ProfessorOut getProfessor() {
		return professor;
	}
	public void setProfessor(ProfessorOut professor) {
		this.professor = professor;
	}
	public String getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}	
	
	public void addAluno(AlunoOut aluno) {
		if(this.alunos != null)
			this.alunos = new ArrayList<AlunoOut>();
		if(aluno != null)
			this.alunos.add(aluno);
	}
	
	public void addQuestionario(QuestionarioDTO questionario) {
		if(this.questionarios != null)
			this.questionarios = new ArrayList<QuestionarioDTO>();
		if(questionario != null)
			this.questionarios.add(questionario);
	}
	
	

}
