package br.com.rest.model.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import br.com.rest.model.entity.AlunoEntity_;
import br.com.rest.model.entity.AlunoQuestionarioREL;
import br.com.rest.model.entity.AlunoQuestionarioREL_;
import br.com.rest.model.entity.GrupoAluno_;
import br.com.rest.model.entity.QuestionarioEntity_;
import br.com.rest.model.entity.TurmaEntity;
import br.com.rest.model.entity.TurmaEntity_;
import br.com.rest.services.TurmaServices;

public class AlunoQuestionarioDAO extends GenericDAO<AlunoQuestionarioREL>{
	
	private static AlunoQuestionarioDAO instance = null;
	
	private AlunoQuestionarioDAO() {
		super(AlunoQuestionarioREL.class, PersistenceManager.getEntityManager());
	}
	
	public synchronized static AlunoQuestionarioDAO getInstance() {
		if(instance == null) {
			instance = new AlunoQuestionarioDAO();
		}
		return instance;
	}
	
	public Set<AlunoQuestionarioREL> dynamicQueryFiltro(Long idQuestionario, String matricula, Date startDate, Date endDate, String nivel, String turma) {
		em.clear();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		//Metamodel m = em.getMetamodel();
		
		CriteriaQuery<AlunoQuestionarioREL> query = cb.createQuery(AlunoQuestionarioREL.class);
		Root<AlunoQuestionarioREL> rootEstilo = query.from(AlunoQuestionarioREL.class);
		Join<AlunoQuestionarioREL, TurmaEntity> rootJoinPerfilTurma;
		
		//EntityType<TurmaEntity> turma_ = m.entity(TurmaEntity.class);
		
		//if Turna != null, colocar um where idTurma do questionario = id turma
		
		query.select(rootEstilo).distinct(true);
		
		Predicate pred = cb.and();
		
		pred = cb.equal(rootEstilo.get(AlunoQuestionarioREL_.QUESTIONARIO).get(QuestionarioEntity_.ID_QUESTIONARIO), idQuestionario);
		
		if(startDate != null && endDate != null){
			pred = cb.and(pred, cb.between(rootEstilo.get(AlunoQuestionarioREL_.DATA_REALIZADO), startDate, endDate));
		} else if(startDate != null && endDate == null) {
			pred = cb.and(pred, cb.greaterThan(rootEstilo.get(AlunoQuestionarioREL_.DATA_REALIZADO), startDate));
		} else if(startDate == null && endDate != null) {
			pred = cb.and(pred, cb.lessThan(rootEstilo.get(AlunoQuestionarioREL_.DATA_REALIZADO), endDate));
		}
		
		if(matricula != null) {
			pred = cb.and(pred, cb.equal(rootEstilo.get(AlunoQuestionarioREL_.ALUNO).get(AlunoEntity_.MATRICULA), matricula));
		} else {
			if(turma != null) {
				rootJoinPerfilTurma = rootEstilo.join(TurmaEntity_.CODIGO);
								
				pred = cb.and(pred, cb.equal(rootJoinPerfilTurma.get(TurmaEntity_.CODIGO), turma));
				//pred = cb.and(pred, rootEstilo.get(AlunoQuestionarioREL_.QUESTIONARIO).in(rootJoinPerfilTurma.join(TurmaEntity_.QUESTIONARIOS)));
			}
			
			
		}
		query.where(pred);
		query.orderBy(cb.asc(rootEstilo.get(AlunoQuestionarioREL_.DATA_REALIZADO)));
		
		TypedQuery<AlunoQuestionarioREL> typedQuery = em.createQuery(query);
		Set<AlunoQuestionarioREL> retorno = new HashSet<AlunoQuestionarioREL>();
		
		retorno.addAll(typedQuery.getResultList());
			
	
		return retorno;
	}
	
	@SuppressWarnings("unchecked")
	public AlunoQuestionarioREL findPerfilPorUltimaData(String matriculaAluno, Long idQuestionario) {
		AlunoQuestionarioREL alunoQuestionario = null;
		em.clear();
		
		alunoQuestionario = (AlunoQuestionarioREL) em.createQuery(
					"Select distinct a "
					+ "from AlunoQuestionarioPerfil a"
					+ " Where UPPER(a.aluno.matricula) = :matriculaAluno"
					+ " AND a.questionario.idQuestionario = :idQuestionario"
					+ " ORDER BY a.dataRealizado desc"
					)
				.setParameter("matriculaAluno", matriculaAluno.toUpperCase())
				.setParameter("idQuestionario", idQuestionario)
				.setMaxResults(1)
				.getSingleResult();
		
				
		return alunoQuestionario;
	}
}
