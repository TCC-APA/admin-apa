package br.com.rest.model.dto;

public class ValorAlternativaDTO {
	
	private Integer valor;
	private String textoAlternativa;
	
	public Integer getValor() {
		return valor;
	}
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	public String getTextoAlternativa() {
		return textoAlternativa;
	}
	public void setTextoAlternativa(String textoAlternativa) {
		this.textoAlternativa = textoAlternativa;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((textoAlternativa == null) ? 0 : textoAlternativa.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		ValorAlternativaDTO other = (ValorAlternativaDTO) obj;
		if (textoAlternativa == null) {
			if (other.textoAlternativa != null)
				return false;
		} else if (!textoAlternativa.equals(other.textoAlternativa))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ValorAlternativaDTO [valor=" + valor + ", textoAlternativa=" + textoAlternativa + "]";
	}
	
	

}
