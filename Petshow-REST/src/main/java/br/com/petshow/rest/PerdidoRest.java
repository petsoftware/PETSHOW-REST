package br.com.petshow.rest;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.exceptions.ExceptionNotFoundRecord;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Perdido;
import br.com.petshow.objects.query.PerdidoQuery;
import br.com.petshow.role.AdocaoRole;
import br.com.petshow.role.PerdidoRole;
import br.com.petshow.util.RestUtil;

@Component
@Path("/perdido")
public class PerdidoRest extends SuperRestClass {
	PerdidoRole perdidoRole;
	
	@GET
	@Path("consulta/perdido/usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response perdidosPorUsuario(@PathParam("idUsuario") long idUsuario){
		List<Perdido> perdidos =null;
		try {
			perdidoRole = getContext().getBean(PerdidoRole.class);
			perdidos = perdidoRole.consultaPorUsuario(idUsuario);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(perdidos).build();

	}
	
	@POST
	@Path("consulta/perdidos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response animaisPerdidos(PerdidoQuery query){

		List<Perdido> animaisPerdidos =null;
		try {
			perdidoRole = getContext().getBean(PerdidoRole.class);
			query.setLimiteRegistros(30);
			animaisPerdidos = perdidoRole.consultaAnimaisPerdidos(query);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(animaisPerdidos).build();

	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id){
		try {
			perdidoRole = getContext().getBean(PerdidoRole.class);
			perdidoRole.delete(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (ExceptionNotFoundRecord e) {
			return RestUtil.getResponseValidationErro(e.getMessage());
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
	}

}
