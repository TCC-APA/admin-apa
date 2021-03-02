package br.com.rest.model.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PerfilAlunoOut extends DefaultReturn{

	private Long idPerfil;
	private String matriculaAluno;
	private String nomeAluno;
	private Long idAluno;
	private List<EstiloDTO> estilos;
	private Date dataRealizado;
	private String nomeQuestionario;
	private Long idQuestionario;
	private Map<EstiloDTO, Long> pontuacaoPorEstilo;

	public Long getIdPerfil() {
		return idPerfil;
	}

	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}

	public String getMatriculaAluno() {
		return matriculaAluno;
	}

	public void setMatriculaAluno(String matriculaAluno) {
		this.matriculaAluno = matriculaAluno;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

	public Date getDataRealizado() {
		return dataRealizado;
	}

	public void setDataRealizado(Date dataRealizado) {
		this.dataRealizado = dataRealizado;
	}

	public String getNomeQuestionario() {
		return nomeQuestionario;
	}

	public void setNomeQuestionario(String nomeQuestionario) {
		this.nomeQuestionario = nomeQuestionario;
	}

	public Long getIdQuestionario() {
		return idQuestionario;
	}

	public void setIdQuestionario(Long idQuestionario) {
		this.idQuestionario = idQuestionario;
	}

	public Map<EstiloDTO, Long> getPontuacaoPorEstilo() {
		return pontuacaoPorEstilo;
	}

	public void setPontuacaoPorEstilo(Map<EstiloDTO, Long> pontuacaoPorEstilo) {
		this.pontuacaoPorEstilo = pontuacaoPorEstilo;
	}

	public List<EstiloDTO> getEstilos() {
		return estilos;
	}

	public void setEstilos(List<EstiloDTO> estilos) {
		this.estilos = estilos;
	}

	public void addEstilo(EstiloDTO estilo) {
		
		if(estilo != null) {
			if(this.estilos == null)
				this.estilos = new ArrayList<EstiloDTO>();
		}
		this.estilos.add(estilo);
	}
}
