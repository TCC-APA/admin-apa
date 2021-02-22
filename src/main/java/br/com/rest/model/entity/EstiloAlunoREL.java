package br.com.rest.model.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity(name = "AlunoQuestionarioPerfil")
@Table(name = "REL_ALUNO_QUESTIONARIO")
@XmlRootElement
public class EstiloAlunoREL implements Serializable {

	private static final long serialVersionUID = 2241828347376645004L;

	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "fk_aluno")
	private AlunoEntity aluno;
	
	@ManyToOne
	@JoinColumn(name = "fk_questionario")
	private QuestionarioEntity questionario;

	@JoinTable(name="COL_PONTUACAO_ESTILOS", joinColumns=@JoinColumn(name="id"))
	@MapKeyColumn (name="estilo")
	@Column(name="pontuacaoPorEstilo")
	@ElementCollection
	private Map<EstiloEntity, Long> pontuacaoPorEstilo;
	
	@Column
	private Date dataRealizado;
	
	public QuestionarioEntity getQuestionario() {
		return questionario;
	}

	public void setQuestionario(QuestionarioEntity questionario) {
		this.questionario = questionario;
	}

	public Date getDataRealizado() {
		return dataRealizado;
	}

	public void setDataRealizado(Date dataRealizado) {
		this.dataRealizado = dataRealizado;
	}

	public Long getIdPerfil() {
		return id;
	}

	public void setIdPerfil(Long idPerfil) {
		this.id = idPerfil;
	}

	public AlunoEntity getAluno() {
		return aluno;
	}

	public void setAluno(AlunoEntity aluno) {
		this.aluno = aluno;
	}

	public Map<EstiloEntity, Long> getPontuacaoPorEstilo() {
		return pontuacaoPorEstilo;
	}

	public void setPontuacaoPorEstilo(Map<EstiloEntity, Long> pontuacaoPorEstilo) {
		this.pontuacaoPorEstilo = pontuacaoPorEstilo;
	}

	@Override
	public String toString() {
		return "EstiloAlunoREL [id=" + id + ", aluno=" + aluno + ", questionario=" + questionario
				+ ", pontuacaoPorEstilo=" + pontuacaoPorEstilo + ", dataRealizado=" + dataRealizado + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aluno == null) ? 0 : aluno.hashCode());
		result = prime * result + ((dataRealizado == null) ? 0 : dataRealizado.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pontuacaoPorEstilo == null) ? 0 : pontuacaoPorEstilo.hashCode());
		result = prime * result + ((questionario == null) ? 0 : questionario.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EstiloAlunoREL other = (EstiloAlunoREL) obj;
		if (aluno == null) {
			if (other.aluno != null)
				return false;
		} else if (!aluno.equals(other.aluno))
			return false;
		if (dataRealizado == null) {
			if (other.dataRealizado != null)
				return false;
		} else if (!dataRealizado.equals(other.dataRealizado))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pontuacaoPorEstilo == null) {
			if (other.pontuacaoPorEstilo != null)
				return false;
		} else if (!pontuacaoPorEstilo.equals(other.pontuacaoPorEstilo))
			return false;
		if (questionario == null) {
			if (other.questionario != null)
				return false;
		} else if (!questionario.equals(other.questionario))
			return false;
		return true;
	}
	


}
