package br.com.rest.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AlunoSimplifiedOut{

	private Long id;
	private String nome;
	private String matricula;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
}
