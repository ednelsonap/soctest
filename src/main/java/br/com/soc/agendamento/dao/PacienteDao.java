package br.com.soc.agendamento.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.soc.agendamento.model.Paciente;
import br.com.soc.agendamento.util.JPAUtil;

public class PacienteDao implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<Paciente> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Paciente>(this.em, Paciente.class);
	}
	
	public boolean nomePacienteExiste(Paciente paciente){
		EntityManager em = new JPAUtil().getEntityManager();
		TypedQuery<Paciente> query = em.createQuery(
				  " select p from Paciente p "
				+ " where p.nome = :pNome", Paciente.class);
		
		query.setParameter("pNome", paciente.getNome());
		
		try {
			Paciente resultado =  query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		
		return true;
	}

	public void adiciona(Paciente t) {
		dao.adiciona(t);
	}

	public void remove(Paciente t) {
		dao.remove(t);
	}

	public void atualiza(Paciente t) {
		dao.atualiza(t);
	}

	public List<Paciente> listaTodos() {
		return dao.listaTodos();
	}

	public Paciente buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
	
	
}
