package br.com.rest.services;

import javax.persistence.NoResultException;

import br.com.rest.model.dao.PersistenceManager;
import br.com.rest.model.dao.TurmaDAO;
import br.com.rest.model.entity.TurmaEntity;

public class TurmaServices {
	
	private static TurmaDAO turmaDao = TurmaDAO.getInstance();
	
	
	public static Boolean incluirTurma(TurmaEntity turma) {
		TurmaEntity turmaBanco = null;
		try {
			turmaBanco = turmaDao.buscarByCodigo(turma.getCodigo());
		} catch (NoResultException e) {
			System.out.println("Turma não encontrada no banco, pode ser incluído.");
		
		}
		
		if(turmaBanco != null) {
			return false;
		} else {
			PersistenceManager.getTransaction().begin();
			
			try{
				turmaDao.incluir(turma);	
				PersistenceManager.getTransaction().commit();
				return true;
			}catch(Exception e){
				PersistenceManager.getTransaction().rollback();
				return false;
			}
		}
	}
	
	public static TurmaEntity consultarTurmaPorCodigo(String codigo) {
		TurmaEntity turma = null;
		try {
			turma = turmaDao.buscarByCodigo(codigo);
			return turma;
		} catch(NoResultException e) {
			return turma;
		}
	}
}
