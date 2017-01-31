package br.com.petshow.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.AnuncioRole;
import br.com.petshow.role.BairroRole;
import br.com.petshow.role.CidadeRole;
import br.com.petshow.role.EstadoRole;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.util.RestUtil;
@Component
@Path("/email")
public class EmailRest extends SuperRestClass{
	
	
	
	@POST
	@Path("enviar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response enviarEmail(HashMap<String,String> parametros){

		try{
		inicializar();
		System.out.println("corpo do email:"+parametros.get("mensagem"));

		//} catch (ExceptionValidation e) {
		//	return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
		
	}
	
	
	
	
	
	
}

