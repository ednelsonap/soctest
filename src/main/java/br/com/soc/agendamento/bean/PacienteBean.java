package br.com.soc.agendamento.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;

import br.com.soc.agendamento.dao.PacienteDao;
import br.com.soc.agendamento.model.Paciente;

@Named
@ViewScoped
public class PacienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Paciente paciente = new Paciente();

	@Inject
	private PacienteDao pacienteDao;
	@Inject
	private FacesContext context;
	
	private List<Paciente> pacientes;

	@PostConstruct
	public void init() {
		this.pacientes = pacienteDao.listaTodos();
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	@Transactional
	public void salvar() {
		System.out.println("Gravando paciente " + this.paciente.getNome());

		boolean nomeExiste = pacienteDao.nomePacienteExiste(this.paciente);
		boolean cpfExiste = pacienteDao.cpfPacienteExiste(this.paciente);

		if (nomeExiste && this.paciente.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Nome " + this.paciente.getNome() + " já cadastrado!", null));

		} else if (cpfExiste && this.paciente.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"CPF " + this.paciente.getCpf() + " já cadastrado!", null));
		} else {
			pacienteDao.adiciona(this.paciente);
			context.addMessage(null, new FacesMessage("Paciente " + this.paciente.getNome() + " salvo!"));
		}
		this.paciente = new Paciente();
		this.pacientes = pacienteDao.listaTodos();
	}

	@Transactional
	public void alterar() {
		System.out.println("Alterando paciente " + this.paciente.getNome());

		try {
			pacienteDao.atualiza(this.paciente);
			context.addMessage(null, new FacesMessage("Paciente " + paciente.getNome() + " alterado!"));

		} catch (PersistenceException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível salvar este paciente! Verifique se não há duplicidade de nome.", null));
		}
		this.paciente = new Paciente();
		this.pacientes = pacienteDao.listaTodos();
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
		this.pacientes = pacienteDao.listaTodos();
	}
	
	public boolean exibirBotaoAlterar(Paciente paciente) {
		if (this.paciente.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean exibirBotaoSalvar(Paciente paciente) {
		if (this.paciente.getId() == null) {
			return true;
		} else {
			return false;
		}
	}

	public void limpar() {
		this.paciente = new Paciente();
		PrimeFaces.current().resetInputs("formPaciente:panelGridCadastro");
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

}
