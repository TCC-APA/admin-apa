package br.com.rest.model.dto.filtro.retorno;

import java.util.List;
import java.util.Map;

import br.com.rest.model.dto.RangePontuacaoClassificacaoDTO;

public class PerfilAlunoFiltroProfessor {
	private Long id;
	private String nome;
	private String matricula;
	private Map<Long, RangePontuacaoClassificacaoDTO> estilosRangesPontuacao;
	private List<Long> estilosPredominantes;
	
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
	public Map<Long, RangePontuacaoClassificacaoDTO> getEstilosRangesPontuacao() {
		return estilosRangesPontuacao;
	}
	public void setEstilosRangesPontuacao(Map<Long, RangePontuacaoClassificacaoDTO> estilosRangesPontuacao) {
		this.estilosRangesPontuacao = estilosRangesPontuacao;
	}
	public List<Long> getEstilosPredominantes() {
		return estilosPredominantes;
	}
	public void setEstilosPredominantes(List<Long> estilosPredominantes) {
		this.estilosPredominantes = estilosPredominantes;
	}
	
}
