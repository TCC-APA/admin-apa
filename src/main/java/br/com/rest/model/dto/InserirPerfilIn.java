package br.com.rest.model.dto;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class InserirPerfilIn {

	private String matriculaAluno;
	private Long idAluno;
	private String dataRealizado;
	private Long idQuestionario;
	
	private Map<Long, Long> pontuacaoPorEstilo; //Key: Id Estilo, Value: pontuacao

	public String getMatriculaAluno() {
		return matriculaAluno;
	}

	public void setMatriculaAluno(String matriculaAluno) {
		this.matriculaAluno = matriculaAluno;
	}

	public Long getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(Long idAluno) {
		this.idAluno = idAluno;
	}

	public String getDataRealizado() {
		return dataRealizado;
	}

	public void setDataRealizado(String dataRealizado) {
		this.dataRealizado = dataRealizado;
	}

	public Long getIdQuestionario() {
		return idQuestionario;
	}

	public void setIdQuestionario(Long idQuestionario) {
		this.idQuestionario = idQuestionario;
	}

	public Map<Long, Long> getPontuacaoPorEstilo() {
		return pontuacaoPorEstilo;
	}

	public void setPontuacaoPorEstilo(Map<Long, Long> pontuacaoPorEstilo) {
		this.pontuacaoPorEstilo = pontuacaoPorEstilo;
	}

	@Override
	public String toString() {
		return "InserirPerfilIn [matriculaAluno=" + matriculaAluno + ", idAluno=" + idAluno + ", dataRealizado="
				+ dataRealizado + ", idQuestionario=" + idQuestionario + ", pontuacaoPorEstilo=" + pontuacaoPorEstilo
				+ "]";
	}
}
