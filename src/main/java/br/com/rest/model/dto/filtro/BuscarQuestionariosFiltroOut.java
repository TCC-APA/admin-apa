package br.com.rest.model.dto.filtro;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.rest.model.dto.DefaultReturn;

@XmlRootElement
public class BuscarQuestionariosFiltroOut extends DefaultReturn{

	List<QuestionarioFiltroOut> questionarios;

	public List<QuestionarioFiltroOut> getQuestionarios() {
		return questionarios;
	}

	public void setQuestionarios(List<QuestionarioFiltroOut> questionarios) {
		this.questionarios = questionarios;
	}
	
	public void addQuestionario(QuestionarioFiltroOut questionario) {
		if(this.questionarios == null)
			this.questionarios = new ArrayList<QuestionarioFiltroOut>();
		if(questionario != null)
			this.questionarios.add(questionario);
	}
}
