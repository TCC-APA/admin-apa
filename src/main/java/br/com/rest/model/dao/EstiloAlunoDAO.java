package br.com.rest.model.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.rest.model.entity.AlunoEntity_;
import br.com.rest.model.entity.EstiloAlunoEntity_;
import br.com.rest.model.entity.EstiloAlunoREL;
import br.com.rest.model.entity.GrupoAluno;
import br.com.rest.model.entity.GrupoAluno_;
import br.com.rest.model.entity.QuestionarioEntity_;
import br.com.rest.model.entity.TurmaEntity;
import br.com.rest.model.entity.TurmaEntity_;
import br.com.rest.services.TurmaServices;

public class EstiloAlunoDAO extends GenericDAO<EstiloAlunoREL>{
	
	private static EstiloAlunoDAO instance = null;
	
	private EstiloAlunoDAO() {
		super(EstiloAlunoREL.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static EstiloAlunoDAO getInstance() {
		if(instance == null) {
			instance = new EstiloAlunoDAO();
		}
		return instance;
	}
	
	public Set<EstiloAlunoREL> dynamicQueryFiltro(Long idQuestionario, String matricula, Date startDate, Date endDate, String nivel, String turma) {
		em.clear();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<EstiloAlunoREL> query = cb.createQuery(EstiloAlunoREL.class);
		Root<EstiloAlunoREL> rootEstilo = query.from(EstiloAlunoREL.class);
		Root<GrupoAluno> rootGrupo = query.from(GrupoAluno.class);
		//if Turna != null, colocar um where idTurma do questionario = id turma
		
		query.select(rootEstilo).distinct(true);
		
		Predicate pred = cb.and();
		
		pred = cb.equal(rootEstilo.get(EstiloAlunoEntity_.QUESTIONARIO).get(QuestionarioEntity_.ID_QUESTIONARIO), idQuestionario);
		
		if(matricula != null) {
			pred = cb.and(cb.equal(rootEstilo.get(EstiloAlunoEntity_.ALUNO).get(AlunoEntity_.MATRICULA), matricula));
		} else {
			if(startDate != null && endDate != null){
				pred = cb.and(pred, cb.between(rootEstilo.get(EstiloAlunoEntity_.DATA_REALIZADO), startDate, endDate));
			} else if(startDate != null && endDate == null) {
				pred = cb.and(cb.greaterThan(rootEstilo.get(EstiloAlunoEntity_.DATA_REALIZADO), startDate));
			} else if(startDate == null && endDate != null) {
				pred = cb.and(cb.lessThan(rootEstilo.get(EstiloAlunoEntity_.DATA_REALIZADO), endDate));
			}
			
			if(nivel != null) {
				
			}
			
			if(turma != null) {
				Long idTurma = 0L;
				TurmaEntity turmaBanco = TurmaServices.consultarTurmaPorCodigo(turma);
				if(turmaBanco != null) 
					idTurma = turmaBanco.getId();
				
				pred = cb.and(pred, cb.equal(rootEstilo.get(EstiloAlunoEntity_.QUESTIONARIO).get(QuestionarioEntity_.ID_QUESTIONARIO),
						rootGrupo.join(TurmaEntity_.QUESTIONARIOS).get(QuestionarioEntity_.ID_QUESTIONARIO)));
				pred = cb.and(pred, cb.equal(rootGrupo.get(GrupoAluno_.ID), idTurma));
			}
		}
		query.where(pred);
		
		TypedQuery<EstiloAlunoREL> typedQuery = em.createQuery(query);
		Set<EstiloAlunoREL> retorno = new HashSet<EstiloAlunoREL>();
		if(matricula != null) {
			retorno.add(typedQuery.getSingleResult());
		} else {
			retorno.addAll(typedQuery.getResultList());
		}
		
	
		return retorno;
	}
	 
}
