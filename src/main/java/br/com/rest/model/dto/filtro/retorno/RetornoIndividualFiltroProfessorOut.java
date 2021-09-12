package br.com.rest.model.dto.filtro.retorno;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.rest.model.dto.EstiloDTO;

@XmlRootElement
public class RetornoIndividualFiltroProfessorOut implements FiltroRetorno{
	
	private List<EstiloDTO> estilos;
	private List<PerfilAlunoFiltroProfessor> perfilAlunoFiltroProfessor;

	public List<EstiloDTO> getEstilos() {
		return estilos;
	}
	public void setEstilos(List<EstiloDTO> estilos) {
		this.estilos = estilos;
	}
	public List<PerfilAlunoFiltroProfessor> getPerfilAlunoFiltroProfessor() {
		return perfilAlunoFiltroProfessor;
	}
	public void setPerfilAlunoFiltroProfessor(List<PerfilAlunoFiltroProfessor> perfilAlunoFiltroProfessor) {
		this.perfilAlunoFiltroProfessor = perfilAlunoFiltroProfessor;
	}
	
	
	
}
