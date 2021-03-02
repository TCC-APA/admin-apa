package br.com.rest.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DefaultReturn {
	
	private List<String> erros;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}
	
	public void addErro(String erro) {
		if(this.erros == null)
			this.erros = new ArrayList<String>();
		
		this.erros.add(erro);
	}
}
