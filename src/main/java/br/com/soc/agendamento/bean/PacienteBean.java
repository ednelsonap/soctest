package br.com.soc.agendamento.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import br.com.soc.agendamento.model.Paciente;
import br.com.soc.agendamento.service.PacienteService;

@Named
@ViewScoped
public class PacienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Paciente paciente = new Paciente();
	
	private List<Paciente> pacientes;
	
	@Inject
	private PacienteService pacienteService;
	
	@PostConstruct
	public void init() {
		this.pacientes = this.pacienteService.getPacientes();
	}
	
	public void salvar() {	
		pacienteService.salvar(this.paciente);
		this.paciente = new Paciente();
		this.pacientes = this.pacienteService.getPacientes();
	}

	public void alterar() {
		this.pacienteService.alterar(this.paciente);
		this.paciente = new Paciente();
		this.pacientes = this.pacienteService.getPacientes();
	}

	public void remover(Paciente paciente) {
		this.pacienteService.remover(paciente);
		this.pacientes = this.pacienteService.getPacientes();
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

	public List<Paciente> ordenarPorNome(){
		this.pacientes = pacienteService.getPacientesOrdenadosPorNome();
		return pacientes;
	}
	
	public void ordenarPorDataDeNascimento(){
		
	}
	
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public List<Paciente> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Paciente> pacientes) {
		this.pacientes = pacientes;
	}
}
