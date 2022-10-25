package it.prova.gestioneproprietario.dao.proprietario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneproprietari.model.Proprietario;


public class ProprietarioDAOImpl implements ProprietarioDAO{
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public List<Proprietario> list() throws Exception {
	
		return entityManager.createQuery("from Proprietario", Proprietario.class).getResultList();
	}

	@Override
	public Proprietario get(Long id) throws Exception {
		return entityManager.find(Proprietario.class, id);
	}

	@Override
	public Proprietario getEagerAutomobili(Long id) throws Exception {
		
		TypedQuery<Proprietario> query = entityManager
				.createQuery("from Proprietario p left join fetch p.automobili where p.id = ?1", Proprietario.class);
		query.setParameter(1, id);
		
		
		return query.getResultStream().findFirst().orElse(null);
	}

	@Override
	public void update(Proprietario proprietarioInstance) throws Exception {
		if (proprietarioInstance == null) {
			throw new Exception("Problema valore in input");
		}
		proprietarioInstance = entityManager.merge(proprietarioInstance);
	}

	@Override
	public void insert(Proprietario proprietarioInstance) throws Exception {
		if (proprietarioInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(proprietarioInstance);
	}

	@Override
	public void delete(Proprietario proprietarioInstance) throws Exception {
		if (proprietarioInstance == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(proprietarioInstance));
	}
	
	@Override
	public int countProprietariConAutoImmatricolataDopo(int inizio) throws Exception{
		if(inizio<0)
			throw new Exception("Problema valore in input");
		
		TypedQuery<Long> query=entityManager.createQuery("select count(distinct proprietario_id) from Automobile where annoimmatricolazione >=?1 ", Long.class);
		query.setParameter(1,inizio);
		return query.getSingleResult().intValue();
	}


}
