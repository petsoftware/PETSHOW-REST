package br.com.petshow.rest;

import java.util.ArrayList;
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

import br.com.petshow.constants.RestConstants;
import br.com.petshow.constants.RestPathConstants;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Evento;
import br.com.petshow.role.EventoRole;

@Component
@Path(RestPathConstants.PATH_EVENTO)
public class EventoRest extends SuperRestClass {

	private EventoRole role;
	@POST
	@Path(RestConstants.REST_PATTERN_URL_INSERT)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postEventoRecord(Evento evento){
		System.out.println("Evento");
		inicializar();
		role = getContext().getBean(EventoRole.class);
		try {
			evento = role.insert(evento);
			return Response.status(200).entity("Salvo com sucesso").build();
		} catch (Exception e) {
			return getResponseException(e);
		}
	}
	
	@GET
	@Path(RestConstants.REST_PATTERN_URL_GETALL)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Evento> findAll() {
		List<Evento> list = new ArrayList<>();	 
		inicializar();
		role = getContext().getBean(EventoRole.class);
		list = role.findAll();
		return list;
	}
	
	@GET
	@Path(RestConstants.REST_PATTERN_URL_GET +"/{codigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Evento find(@PathParam("codigo") long codigo) {
		Evento evento;
		inicializar();
		role = getContext().getBean(EventoRole.class);
		try {
			evento = role.find(codigo);
			return evento;
		} catch (ExceptionValidation e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
