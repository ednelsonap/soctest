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

import br.com.soc.agendamento.dao.ExameDao;
import br.com.soc.agendamento.model.Exame;

@Named
@ViewScoped
public class ExameBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Exame exame = new Exame();
	
	@Inject
	private ExameDao exameDao;

	@Inject
	private FacesContext context;

	private List<Exame> exames;
	
	@PostConstruct
	public void init() {
		this.exames = exameDao.listaTodos();
	}
	
	public List<Exame> getExames(){
		return exames;
	}

	@Transactional
	public void salvar() {
		System.out.println("Gravando exame " + this.exame.getNome());
		
		boolean nomeExiste = exameDao.nomeExameExiste(this.exame);

		if (nomeExiste && this.exame.getId() == null) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
					"O exame " + this.exame.getNome() + " já está cadastrado!", null));
		} else {
			exameDao.adiciona(this.exame);
			context.addMessage(null,
					new FacesMessage("Exame " + this.exame.getNome() + " cadastrado com sucesso!"));
		}
		this.exame = new Exame();
	}

	@Transactional
	public void alterar() {
		System.out.println("Alterando exame " + this.exame.getNome());

		try {
			exameDao.atualiza(this.exame);
			context.addMessage(null,
					new FacesMessage("Exame " + exame.getNome() + " alterado!"));

		} catch (PersistenceException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Não foi possível salvar este exame! Verifique se não há duplicidade de nome.",
							null));
		}
		this.exame = new Exame();
	}
	
	public boolean exibirBotaoAlterar(Exame exame) {
		if (this.exame.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean exibirBotaoSalvar(Exame exame) {
		if (this.exame.getId() == null) {
			return true;
		} else {
			return false;
		}
	}
	
	@Transactional
	public void remover(Exame exame) {
		System.out.println("Removendo exame " + exame.getNome());

		try {
			exameDao.remove(exame);
			context.addMessage(null,
					new FacesMessage("Exame " + exame.getNome() + " removido!"));
		} catch (PersistenceException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível remover este exame!", null));
		}
	}

	public void limpar() {
		this.exame = new Exame();
		PrimeFaces.current().resetInputs("formExame:panelGridCadastro");
	}

	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}

}
