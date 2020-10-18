package br.com.soc.agendamento.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.soc.agendamento.model.Agendamento;

public class AgendamentoDao implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	EntityManager em;
	
	private DAO<Agendamento> dao;
	
	@PostConstruct
	void init(){
		this.dao = new DAO<Agendamento>(this.em, Agendamento.class);
	}
	
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
