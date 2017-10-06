package br.com.petshow.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.objects.EnviarMensagem;
import br.com.petshow.role.RoleSendEmail;
import br.com.petshow.util.RestUtil;
@Component
@Path("/email")
public class EmailRest extends SuperRestClass{
	
	private RoleSendEmail roleSendEmail;
	
	@POST
	@Path("enviar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response enviarEmail(EnviarMensagem enviarMensagem){
		roleSendEmail = getContext().getBean(RoleSendEmail.class);
		try{
			roleSendEmail.enviar(enviarMensagem);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(enviarMensagem).build();
		
	}
	
}

