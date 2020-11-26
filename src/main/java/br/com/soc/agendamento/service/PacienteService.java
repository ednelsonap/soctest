package br.com.soc.agendamento.service;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import br.com.soc.agendamento.dao.PacienteDao;
import br.com.soc.agendamento.model.Paciente;

public class PacienteService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private PacienteDao pacienteDao;
	@Inject
	private FacesContext context;
	
	private List<Paciente> pacientes;

	public List<Paciente> getPacientes(){
		pacientes = pacienteDao.listaTodos();
		return pacientes;
	}
	
	@Transactional
	public void salvar(Paciente paciente) {
	
		boolean nomeExiste = pacienteDao.nomePacienteExiste(paciente);
		boolean cpfExiste = pacienteDao.cpfPacienteExiste(paciente);

		if (nomeExiste && paciente.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Nome " + paciente.getNome() + " já cadastrado!", null));

		} else if (cpfExiste && paciente.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"CPF " + paciente.getCpf() + " já cadastrado!", null));
		} else {
			pacienteDao.adiciona(paciente);
			context.addMessage(null, new FacesMessage("Paciente " + paciente.getNome() + " salvo!"));
		}
	}

	@Transactional
	public void alterar(Paciente paciente) {
		System.out.println("Alterando paciente " + paciente.getNome());

		try {
			pacienteDao.atualiza(paciente);
			context.addMessage(null, new FacesMessage("Paciente " + paciente.getNome() + " alterado!"));

		} catch (PersistenceException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível salvar este paciente! Verifique se não há duplicidade de nome.", null));
		}

	}

	@Transactional
	public void remover(Paciente paciente) {
		System.out.println("Removendo paciente " + paciente.getNome());

		try {
			pacienteDao.remove(paciente);
			context.addMessage(null, new FacesMessage("Paciente " + paciente.getNome() + " removido!"));
		} catch (PersistenceException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível remover este paciente!", null));
		}
	}

	public List<Paciente> getPacientesOrdenadosPorNome() {
		return null;
	}
}
