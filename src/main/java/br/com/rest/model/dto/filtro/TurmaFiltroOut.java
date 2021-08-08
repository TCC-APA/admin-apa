package br.com.rest.model.dto.filtro;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TurmaFiltroOut{
	private Long id;
	private String codigo;
	private String disciplina;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDisciplina() {
		return disciplina;
	}
	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
}

