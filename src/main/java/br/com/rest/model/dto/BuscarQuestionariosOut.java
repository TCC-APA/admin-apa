package br.com.rest.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BuscarQuestionariosOut extends DefaultReturn{
	List<QuestionarioDTO> questionarios;

	public List<QuestionarioDTO> getQuestionarios() {
		return questionarios;
	}

	public void setQuestionarios(List<QuestionarioDTO> questionarios) {
		this.questionarios = questionarios;
	}
	
	public void addQuestionario(QuestionarioDTO questionario) {
		if(this.questionarios == null)
			this.questionarios = new ArrayList<QuestionarioDTO>();
		
		if(questionario != null)
			this.questionarios.add(questionario);
	}

}
