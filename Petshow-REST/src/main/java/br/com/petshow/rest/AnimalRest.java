package br.com.petshow.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Animal;
import br.com.petshow.role.AdocaoRole;
import br.com.petshow.role.AnimalRole;

import br.com.petshow.util.RestUtil;
@Component
@Path("/animal")
public class AnimalRest  extends SuperRestClass{


	AnimalRole animalRole;
	AdocaoRole adocaoRole;	

	

	@GET
	@Path("consulta/usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response animaisPorUsuario(@PathParam("idUsuario") long idUsuario){

		inicializar();
		List<Animal> animais =null;
		try {
			animalRole = getContext().getBean(AnimalRole.class);
			animais = animalRole.consultaPorUsuario(idUsuario);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(animais).build();

	}


	@GET
	@Path("consulta/adocao/{idEstado}/{idCidade}/{tpAnimal}/{fase}/{sexo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response animaisAdocao(  @PathParam("idEstado") long estado,
									@PathParam("idCidade") long cidade,
									@PathParam("tpAnimal") String tpAnimal,
									@PathParam("fase") String fase,
									@PathParam("sexo") String sexo
									
				){

		inicializar();
		List<Adocao> animais =null;
		try {
			adocaoRole = getContext().getBean(AdocaoRole.class);
			animais =adocaoRole.consultaAnimaisAdocao(estado,cidade,tpAnimal,fase,sexo);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(animais).build();

	}




}
