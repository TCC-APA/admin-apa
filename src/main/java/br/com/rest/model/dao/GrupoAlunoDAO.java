package br.com.rest.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.rest.model.entity.AlunoEntity;
import br.com.rest.model.entity.GrupoAluno;
import br.com.rest.model.entity.QuestionarioEntity;

public class GrupoAlunoDAO extends GenericDAO<GrupoAluno>{
	
	private static GrupoAlunoDAO instance = null;
	
	private GrupoAlunoDAO() {
		super(GrupoAluno.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static GrupoAlunoDAO getInstance() {
		if(instance == null) {
			instance = new GrupoAlunoDAO();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public List<QuestionarioEntity> findQuestionariosByGruposAluno(AlunoEntity aluno){
		List<QuestionarioEntity> listaQuestionarios = new ArrayList<QuestionarioEntity>();
		em.clear();
		
		listaQuestionarios = (List<QuestionarioEntity>) em.createQuery(
					"Select distinct g.questionarios "
					+ "from GrupoAluno g"
					+ " Where :aluno member of g.alunos")
				.setParameter("aluno", aluno)
				.getResultList();
		
		return listaQuestionarios;
	}

}
