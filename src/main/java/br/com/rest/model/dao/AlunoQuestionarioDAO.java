package br.com.rest.model.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import br.com.rest.model.entity.AlunoQuestionarioREL;
import br.com.rest.model.entity.TurmaEntity;

public class AlunoQuestionarioDAO extends GenericDAO<AlunoQuestionarioREL> {

	private static AlunoQuestionarioDAO instance = null;

	private AlunoQuestionarioDAO() {
		super(AlunoQuestionarioREL.class, PersistenceManager.getEntityManager());
	}

	public synchronized static AlunoQuestionarioDAO getInstance() {
		if (instance == null) {
			instance = new AlunoQuestionarioDAO();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<AlunoQuestionarioREL> dynamicQueryFiltro(Long idQuestionario, String nome, Date startDate, Date endDate,
			TurmaEntity turma) {
		em.clear();

		Set<TurmaEntity> turmasAluno = null;

		StringBuilder query = new StringBuilder();
		query.append("Select a from AlunoQuestionarioPerfil a JOIN a.questionario q"
				+ " WHERE q.idQuestionario = :idQuestionario");

		if (startDate != null && endDate != null) {
			query.append(" AND a.dataRealizado BETWEEN :startDate and :endDate");
		} else if (startDate != null && endDate == null) {
			query.append(" AND a.dataRealizado >= :startDate");
		} else if (startDate == null && endDate != null) {
			query.append(" AND a.dataRealizado <= :endDate");
		}

		if (nome != null) {
			query.append(" AND UPPER(a.aluno.nome) = :nome");
		}

		if (turma != null) {
			query.append(" AND a.aluno in :turmaAlunos");
		}
		
		query.append(" AND a.dataRealizado = (select max(a2.dataRealizado)" + 
					 "from AlunoQuestionarioPerfil a2" + 
				 	 "where a.aluno = a2.aluno)");

		query.append(" ORDER BY a.dataRealizado");
		List<AlunoQuestionarioREL> listaPerfil = null;
		System.out.println("query: "+query.toString());
		Query queryDb = em.createQuery(query.toString());

		if (idQuestionario != null)
			queryDb.setParameter("idQuestionario", idQuestionario);
		if (startDate != null)
			queryDb.setParameter("startDate", startDate);
		if (endDate != null)
			queryDb.setParameter("endDate", endDate);
		if (nome != null)
			queryDb.setParameter("nome", nome);
		if (turma != null)
			queryDb.setParameter("turmaAlunos", turma.getAlunos());

		System.out.println("query: " + query.toString());
		listaPerfil = (List<AlunoQuestionarioREL>) queryDb.getResultList();

		return listaPerfil;
		/*
		 * CriteriaBuilder cb = em.getCriteriaBuilder(); //Metamodel m =
		 * em.getMetamodel();
		 * 
		 * CriteriaQuery<AlunoQuestionarioREL> query =
		 * cb.createQuery(AlunoQuestionarioREL.class); Root<AlunoQuestionarioREL>
		 * rootEstilo = query.from(AlunoQuestionarioREL.class);
		 * Join<AlunoQuestionarioREL, TurmaEntity> rootJoinPerfilTurma;
		 * 
		 * 
		 * query.select(rootEstilo).distinct(true);
		 * 
		 * Predicate pred = cb.and();
		 * 
		 * pred = cb.equal(rootEstilo.get(AlunoQuestionarioREL_.QUESTIONARIO).get(
		 * QuestionarioEntity_.ID_QUESTIONARIO), idQuestionario);
		 * 
		 * if(startDate != null && endDate != null){ pred = cb.and(pred,
		 * cb.between(rootEstilo.get(AlunoQuestionarioREL_.DATA_REALIZADO), startDate,
		 * endDate)); } else if(startDate != null && endDate == null) { pred =
		 * cb.and(pred,
		 * cb.greaterThan(rootEstilo.get(AlunoQuestionarioREL_.DATA_REALIZADO),
		 * startDate)); } else if(startDate == null && endDate != null) { pred =
		 * cb.and(pred,
		 * cb.lessThan(rootEstilo.get(AlunoQuestionarioREL_.DATA_REALIZADO), endDate));
		 * }
		 * 
		 * if(matricula != null) { pred = cb.and(pred,
		 * cb.equal(rootEstilo.get(AlunoQuestionarioREL_.ALUNO).get(AlunoEntity_.
		 * MATRICULA), matricula)); } else { if(turma != null) { rootJoinPerfilTurma =
		 * rootEstilo.join(TurmaEntity_.CODIGO); Subquery<Integer> sq =
		 * query.subquery(Integer.class); Root<TurmaEntity> rootTurma =
		 * query.from(TurmaEntity.class); sq.select(sqEmp.get(Employee_.id)).where(
		 * cb.equal(project.get(Project_.name), cb.parameter(String.class, "project")));
		 * 
		 * pred = cb.and(pred, cb.equal(rootJoinPerfilTurma.get(TurmaEntity_.CODIGO),
		 * turma)); pred = cb.and(pred,
		 * cb.inrootEstilo.get(AlunoQuestionarioREL_.ALUNO).in(rootJoinPerfilTurma.get(
		 * TurmaEntity_.ALUNOS))); //pred = cb.and(pred,
		 * rootEstilo.get(AlunoQuestionarioREL_.QUESTIONARIO).in(rootJoinPerfilTurma.
		 * join(TurmaEntity_.QUESTIONARIOS))); }
		 * 
		 * 
		 * } query.where(pred);
		 * query.orderBy(cb.asc(rootEstilo.get(AlunoQuestionarioREL_.DATA_REALIZADO)));
		 * 
		 * TypedQuery<AlunoQuestionarioREL> typedQuery = em.createQuery(query);
		 * Set<AlunoQuestionarioREL> retorno = new HashSet<AlunoQuestionarioREL>();
		 * 
		 * retorno.addAll(typedQuery.getResultList());
		 * 
		 * 
		 * return retorno;
		 */
	}

	@SuppressWarnings("unchecked")
	public AlunoQuestionarioREL findPerfilPorUltimaData(String matriculaAluno, Long idQuestionario) {
		AlunoQuestionarioREL alunoQuestionario = null;
		em.clear();

		alunoQuestionario = (AlunoQuestionarioREL) em
				.createQuery("Select distinct a " + "from AlunoQuestionarioPerfil a"
						+ " Where UPPER(a.aluno.matricula) = :matriculaAluno"
						+ " AND a.questionario.idQuestionario = :idQuestionario" + " ORDER BY a.dataRealizado desc")
				.setParameter("matriculaAluno", matriculaAluno.toUpperCase())
				.setParameter("idQuestionario", idQuestionario).setMaxResults(1).getSingleResult();

		return alunoQuestionario;
	}
}
