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
import br.com.petshow.model.Perdido;
import br.com.petshow.role.AdocaoRole;
import br.com.petshow.role.AnimalRole;
import br.com.petshow.role.PerdidoRole;
import br.com.petshow.util.RestUtil;
@Component
@Path("/animal")
public class AnimalRest  extends SuperRestClass{


	AnimalRole animalRole;
	AdocaoRole adocaoRole;	
	PerdidoRole perdidoRole;

	

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
	@Path("consulta/adocao/{idEstado}/{idCidade}/{tpAnimal}/{fase}/{sexo}/{limiteRegistros}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response animaisAdocao(  @PathParam("idEstado") long estado,
									@PathParam("idCidade") long cidade,
									@PathParam("tpAnimal") String tpAnimal,
									@PathParam("fase") String fase,
									@PathParam("sexo") String sexo,
									@PathParam("limiteRegistros") int limiteRegistros
									
				){

		inicializar();
		List<Adocao> animais =null;
		try {
			adocaoRole = getContext().getBean(AdocaoRole.class);
			animais =adocaoRole.consultaAnimaisAdocao(estado,cidade,tpAnimal,fase,sexo,limiteRegistros);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(animais).build();

	}

	@GET
	@Path("/perdido/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaPerdido(@PathParam("id") long id){

		Perdido entidade=null;
		try {
			inicializar();

			perdidoRole = getContext().getBean(PerdidoRole.class);
			entidade= perdidoRole.find(id);

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
	
	@GET
	@Path("consulta/perdido/{idEstado}/{idCidade}/{idBairro}/{tpAnimal}/{tpPerdidoAchado}/{limiteRegistros}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response animaisPerdidos(  @PathParam("idBairro") long bairro,
								      @PathParam("idEstado") long estado,
									@PathParam("idCidade") long cidade,
									
									@PathParam("tpAnimal") String tpAnimal,
									@PathParam("tpPerdidoAchado") String tpPerdidoAchado,
									@PathParam("limiteRegistros") int limiteRegistros
									
				){

		inicializar();
		List<Perdido> animais =null;
		try {
			perdidoRole = getContext().getBean(PerdidoRole.class);
			animais =perdidoRole.consultaPorFiltros(tpAnimal, tpPerdidoAchado, estado, cidade, bairro,limiteRegistros);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(animais).build();

	}



}
