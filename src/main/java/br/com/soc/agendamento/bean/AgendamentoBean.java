package br.com.soc.agendamento.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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

import br.com.soc.agendamento.dao.AgendamentoDao;
import br.com.soc.agendamento.dao.ExameDao;
import br.com.soc.agendamento.dao.PacienteDao;
import br.com.soc.agendamento.model.Agendamento;
import br.com.soc.agendamento.model.Exame;
import br.com.soc.agendamento.model.Paciente;

@Named
@ViewScoped
public class AgendamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Agendamento agendamento = new Agendamento();

	@Inject
	private PacienteDao pacienteDao;

	@Inject
	private ExameDao exameDao;

	@Inject
	private AgendamentoDao agendamentoDao;

	@Inject
	private FacesContext context;

	private List<Agendamento> agendamentos;

	@PostConstruct
	public void init() {
		this.agendamentos = agendamentoDao.listaTodos();
	}

	public List<Paciente> getPacientes() {
		return pacienteDao.listaTodos();
	}

	public List<Exame> getExames() {
		return exameDao.listaTodos();
	}

	public List<Agendamento> getAgendamentos() {
		return this.agendamentos;
	}

	@Transactional
	public void salvar() {
		try {
			agendamentoDao.adiciona(this.agendamento);
			context.addMessage(null, new FacesMessage("Agendamento cadastrado com sucesso!"));

		} catch (PersistenceException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível salvar este agendamento!", null));
		}
		this.agendamento = new Agendamento();
		this.agendamentos = agendamentoDao.listaTodos();
	}

	@Transactional
	public void alterar() {

		try {
			agendamentoDao.atualiza(this.agendamento);
			context.addMessage(null, new FacesMessage("Agendamento alterado com sucesso!"));

		} catch (PersistenceException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Não foi possível salvar este agendamento! Verifique se não há duplicidade de nome.", null));
		}
		this.agendamento = new Agendamento();
	}

	@Transactional
	public void remover(Agendamento agendamento) {

		try {
			agendamentoDao.remove(agendamento);
			context.addMessage(null, new FacesMessage("Agendamento removido com sucesso!"));
		} catch (PersistenceException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível remover este agendamento!", null));
		}
		this.agendamentos = agendamentoDao.listaTodos();
	}
	
	@Transactional
	public void salvarResultado() {
		try {
			this.agendamento.setStatus("REALIZADO");
			agendamentoDao.atualiza(this.agendamento);
			context.addMessage(null, new FacesMessage("Resultado salvo com sucesso!"));
		} catch (PersistenceException e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar!", null));
		}
	}
	
	public boolean exibirBotaoAlterar(Agendamento agendamento) {
		if (this.agendamento.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean exibirBotaoSalvar(Agendamento agendamento) {
		if (this.agendamento.getId() == null) {
			return true;
		} else {
			return false;
		}
	}

	public Date getDataMinima() {
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.add(Calendar.DATE, +1);
		return dataAtual.getTime();
	}

	public void limpar() {
		this.agendamento = new Agendamento();
		PrimeFaces.current().resetInputs("formAgendamento:panelGridCadastro");
	}

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

}
