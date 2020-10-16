package br.com.soc.agendamento.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;

import br.com.soc.agendamento.dao.AgendamentoDao;
import br.com.soc.agendamento.model.Agendamento;

@Named
@ViewScoped
public class AgendamentoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Agendamento agendamento = new Agendamento();
	
	@Inject
	private AgendamentoDao agendamentoDao;

	@Inject
	private FacesContext context;

	public List<Agendamento> getAgendamentos() {
		return this.agendamentoDao.listaTodos();
	}

	@Transactional
	public void salvar() {
		System.out.println("Gravando agendamento " + this.agendamento.getNomeExame());
		
		boolean nomeExiste = agendamentoDao.nomePacienteExiste(this.agendamento);

		if (nomeExiste && this.agendamento.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Paciente " + this.agendamento.getNomePaciente() + " já está agendado!", null));
		} else {
			agendamentoDao.adiciona(this.agendamento);
			context.addMessage(null,
					new FacesMessage("Agendamento do exame " + this.agendamento.getNomeExame() + " salvo!"));
		}
		this.agendamento = new Agendamento();
	}

	@Transactional
	public void alterar() {
		System.out.println("Alterando agendamento " + this.agendamento.getNomeExame());

		try {
			agendamentoDao.atualiza(this.agendamento);
			context.addMessage(null,
					new FacesMessage("Agendamento " + agendamento.getNomeExame() + " alterado!"));

		} catch (PersistenceException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Não foi possível salvar este agendamento! Verifique se não há duplicidade de nome.",
							null));
		}
		this.agendamento = new Agendamento();
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
	
	public Date getDataMinima(){
		Calendar dataAtual = Calendar.getInstance();
		dataAtual.add(Calendar.DATE, +1);
		return dataAtual.getTime();
	}
	
	@Transactional
	public void remover(Agendamento agendamento) {
		System.out.println("Removendo agendamento do exame " + agendamento.getNomeExame());

		try {
			agendamentoDao.remove(agendamento);
			context.addMessage(null,
					new FacesMessage("Agendamento do exame " + agendamento.getNomeExame() + " removido!"));
		} catch (PersistenceException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível remover este agendamento!", null));
		}
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
