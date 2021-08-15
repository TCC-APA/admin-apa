package br.com.rest.model.dto.filtro;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.rest.model.dto.DefaultReturn;

@XmlRootElement
public class BuscarTurmasFiltroOut{

	List<TurmaFiltroOut> turmas;

	public List<TurmaFiltroOut> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<TurmaFiltroOut> turmas) {
		this.turmas = turmas;
	}
	
	public void addTurma(TurmaFiltroOut turma) {
		if(this.turmas == null)
			this.turmas = new ArrayList<TurmaFiltroOut>();
		if(turma != null)
			this.turmas.add(turma);
	}
}
