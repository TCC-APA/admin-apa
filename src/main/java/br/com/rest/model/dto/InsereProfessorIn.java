package br.com.rest.model.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InsereProfessorIn {
	private String nome;
	private String senha;
	private String siape;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSiape() {
		return siape;
	}
	public void setSiape(String siape) {
		this.siape = siape;
	}
	
	

}
