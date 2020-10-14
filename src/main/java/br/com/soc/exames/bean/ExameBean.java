package br.com.soc.exames.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

import br.com.soc.exames.dao.ExameDao;
import br.com.soc.exames.model.Exame;

@Named
@ViewScoped 
public class ExameBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private Exame exame = new Exame();
	
	@Inject
	private ExameDao exameDao;
	
	@Inject
	private FacesContext context;
	
	public List<Exame> getExames(){
		return this.exameDao.listaTodos();
	}
	
	
	
	@Transactional
	public void salvar() {
		System.out.println("Gravando exame " + this.exame.getNomeExame());
		
		boolean existe = exameDao.nomePacienteExiste(this.exame);
			
		if ((this.exame.getId() == null) && (existe == false)){
			exameDao.adiciona(this.exame);
			context.addMessage(null,
					new FacesMessage("Exame " + exame.getNomeExame() + " salvo!"));
		} else {
			exameDao.atualiza(this.exame);
			context.addMessage(null,
					new FacesMessage("Exame " + exame.getNomeExame() + " alterado!"));
		}
		this.exame = new Exame();
	}

	@Transactional
	public void remover(Exame exame) {
		System.out.println("Removendo Exame " + exame.getNomeExame());
		
		try {
			exameDao.remove(exame);
			context.addMessage(null,
					new FacesMessage("Exame " + exame.getNomeExame() + " removido!"));
		} catch (PersistenceException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Não foi possível remover este exame!", null));
		}
	}
	
	public Exame getExame() {
		return exame;
	}

	public void setExame(Exame exame) {
		this.exame = exame;
	}
	
}
