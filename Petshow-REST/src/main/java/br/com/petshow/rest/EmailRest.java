package br.com.petshow.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.util.RestUtil;
@Component
@Path("/email")
public class EmailRest extends SuperRestClass{
	
	
	
	@POST
	@Path("enviar")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response enviarEmail(HashMap<String,String> parametros){

		try{
		System.out.println("corpo do email:"+parametros.get("mensagem"));

		//} catch (ExceptionValidation e) {
		//	return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
		
	}
	
}

