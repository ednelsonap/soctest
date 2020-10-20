package br.com.soc.agendamento.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

public class ClienteTest {
	
	@Test
    public void testaQueBuscarUmCarrinhoTrasUmCarrinho() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/soctest/webservice");
        String conteudo = target.path("/exames/1").request().get(String.class);
        System.out.println(conteudo);
    }
}
