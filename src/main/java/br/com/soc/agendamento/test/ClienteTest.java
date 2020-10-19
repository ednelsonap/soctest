package br.com.soc.agendamento.test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Test;

public class ClienteTest {
	
	@Test
    public void testaQueBuscarUmCarrinhoTrasUmCarrinho() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/soctest");
        String conteudo = target.path("/exames/1").request().get(String.class);
        System.out.println(conteudo);
       /* Agendamento fromXML = (Agendamento) new XStream().fromXML(conteudo);
        fromXML.getExame().getNome();*/
       //Assert.assertEquals("Rua Vergueiro 3185, 8 andar",fromXML.getRua());
    }
}
