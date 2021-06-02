package br.com.rest.model.dto;

import java.util.ArrayList;
import java.util.List;

public class BuscarTurmasOut extends DefaultReturn {
	
	List<TurmaDTO> turmas;

	public List<TurmaDTO> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<TurmaDTO> turmas) {
		this.turmas = turmas;
	}
	
	public void addTurma(TurmaDTO turma) {
		if(this.turmas != null)
			this.turmas = new ArrayList<TurmaDTO>();
		if(turma != null)
			this.turmas.add(turma);
	}

}
