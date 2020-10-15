package br.com.soc.agendamento.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.soc.agendamento.model.Agendamento;
import br.com.soc.agendamento.util.JPAUtil;

public class AgendamentoDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<Agendamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Agendamento>(this.em, Agendamento.class);
	}
	
	public boolean nomePacienteExiste(Agendamento agendamento){
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Agendamento> query = em.createQuery(
				  " select a from Agendamento a "
				+ " where a.nomePaciente = :pNome", Agendamento.class);
		
		query.setParameter("pNome", agendamento.getNomePaciente());
		
		try {
			Agendamento resultado =  query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		
		return true;
	}
	
	/*public boolean idDistinto(Agendamento agendamento){
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Agendamento> query = em.createQuery(
				  " select a from Agendamento a "
				+ " where a.id != :pId", Agendamento.class);
		
		query.setParameter("pId", agendamento.getId());
		
		try {
			Agendamento resultado =  query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		
		return true;
	}*/
	
	//métodos delegados do DAO
	public void adiciona(Agendamento t) {
		dao.adiciona(t);
	}

	public void remove(Agendamento t) {
		dao.remove(t);
	}

	public void atualiza(Agendamento t) {
		dao.atualiza(t);
	}

	public List<Agendamento> listaTodos() {
		return dao.listaTodos();
	}

	public int contaTodos() {
		return dao.contaTodos();
	}
	
	
}
