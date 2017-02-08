package br.com.petshow.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.context.ApplicationContext;

/**
 * Super Classe para reutilizar metodos nas outras classes REST
 * @author Rafael Rocha
 * @since versao 1.0 - dia 02/12/2016 as 09:42.
 */
public class SuperRestClass {


	private static ApplicationContext context;
	
	public static void setContext(ApplicationContext context) {
		SuperRestClass.context = context;
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	protected Response getResponseException(Exception e) {
		StringBuilder error = new StringBuilder(e.getMessage());
		e.printStackTrace();
		if(e.getCause() != null){
			error.append(e.getCause().getMessage());
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
	}

	public ApplicationContext getContext() {
		return context;
	}
}
