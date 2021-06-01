package br.com.rest.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BuscarPerfilAlunoOut extends DefaultReturn {
	List<PerfilAlunoOut> listaPerfilAlunos;

	public List<PerfilAlunoOut> getListaPerfilAlunos() {
		return listaPerfilAlunos;
	}

	public void setListaPerfilAlunos(List<PerfilAlunoOut> listaPerfilAlunos) {
		this.listaPerfilAlunos = listaPerfilAlunos;
	}
	
	public void addListaPerfilAlunos(PerfilAlunoOut perfilAluno) {
		if(this.listaPerfilAlunos == null)
			this.listaPerfilAlunos = new ArrayList<PerfilAlunoOut>();
		if(perfilAluno != null)
			this.listaPerfilAlunos.add(perfilAluno);
	}
	

}
