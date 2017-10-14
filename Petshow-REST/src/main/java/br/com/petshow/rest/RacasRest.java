package br.com.petshow.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionNotFoundRecord;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Racas;
import br.com.petshow.role.RacasRole;
import br.com.petshow.util.RestUtil;

@Component
@Path("/raca")
public class RacasRest extends SuperRestClass{


	RacasRole racasRole;

	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvar(Racas raca){
 
		racasRole = getContext().getBean(RacasRole.class);
		if(raca.getId()>0){
			try {
				racasRole.update(raca);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				racasRole.insert(raca);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(raca).build();
	}



	@GET
	@Path("consulta/por/tipo/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAnunciosPorUsuario(@PathParam("tipo") EnumTipoAnimal tipoAnimal){

		List<Racas> racas =null;
		try {
			racasRole = getContext().getBean(RacasRole.class);
			racas = racasRole.consultaRacas(tipoAnimal);

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(racas).build();

	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id){
		try {
			racasRole = getContext().getBean(RacasRole.class);
			racasRole.delete(id);
			
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (ExceptionNotFoundRecord e) {
			return RestUtil.getResponseValidationErro(e.getMessage());
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}

		return Response.ok().build();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAnuncio(@PathParam("id") long id){

		Racas anuncioConsultado=null;
		try {
			racasRole = getContext().getBean(RacasRole.class);
			anuncioConsultado=racasRole.find(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(anuncioConsultado).build();
	}

}
