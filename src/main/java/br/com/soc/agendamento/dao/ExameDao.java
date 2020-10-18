package br.com.soc.agendamento.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.soc.agendamento.model.Exame;
import br.com.soc.agendamento.util.JPAUtil;

public class ExameDao implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<Exame> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Exame>(this.em, Exame.class);
	}
	
	public boolean nomeExameExiste(Exame exame){
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Exame> query = em.createQuery(
				  " select e from Exame e "
				+ " where e.nome = :pNome", Exame.class);
		
		query.setParameter("pNome", exame.getNome());
		
		try {
			Exame resultado =  query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		
		return true;
	}

	public void adiciona(Exame t) {
		dao.adiciona(t);
	}

	public void remove(Exame t) {
		dao.remove(t);
	}

	public void atualiza(Exame t) {
		dao.atualiza(t);
	}

	public List<Exame> listaTodos() {
		return dao.listaTodos();
	}

	public Exame buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
	
	
}
