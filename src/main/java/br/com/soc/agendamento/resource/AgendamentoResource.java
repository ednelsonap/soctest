package br.com.soc.agendamento.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.soc.agendamento.dao.AgendamentoDao;
import br.com.soc.agendamento.model.Agendamento;

@Path("exames")
public class AgendamentoResource {

	@Inject
	private AgendamentoDao agendamentoDao;
	
	@Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String busca(@PathParam("id") Integer id) {
        Agendamento agendamento = agendamentoDao.buscaPorId(id);
        return agendamento.toJson();
    }
}