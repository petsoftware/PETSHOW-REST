package br.com.petshow.rest;

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

import br.com.petshow.enums.EnumTipoAnimal;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Perdido;
import br.com.petshow.model.Racas;
import br.com.petshow.model.Servico;
import br.com.petshow.model.Tutor;
import br.com.petshow.model.Venda;
import br.com.petshow.role.AdocaoRole;
import br.com.petshow.role.AnimalRole;
import br.com.petshow.role.PerdidoRole;
import br.com.petshow.role.RacasRole;
import br.com.petshow.role.ServicoRole;
import br.com.petshow.role.TutorRole;
import br.com.petshow.role.VendaRole;
import br.com.petshow.util.RestUtil;
@Component
@Path("/animal")
public class AnimalRest  extends SuperRestClass{


	AnimalRole animalRole;
	AdocaoRole adocaoRole;	
	PerdidoRole perdidoRole;
	RacasRole racasRole;
	TutorRole tutorRole;


	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvar(Animal animal){
 
		animalRole = getContext().getBean(AnimalRole.class);
		if(animal.getId()>0){
			try {
				animalRole.update(animal);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				animalRole.insert(animal);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(animal).build();


	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAnimal(@PathParam("id") long id){

		Animal animal=null;
		try {
			animalRole = getContext().getBean(AnimalRole.class);
			animal=animalRole.find(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(animal).build();
	}

	@GET
	@Path("consulta/usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response animaisPorUsuario(@PathParam("idUsuario") long idUsuario){
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
	
	@GET
	@Path("consulta/tipos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tiposAnimais(	    
				){
		List<String> tiposAnimais =EnumTipoAnimal.toStringArray();
	
		return Response.ok(tiposAnimais).build();

	}
	
	@GET
	@Path("racas/{tipo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tiposAnimais( 	@PathParam("tipo") EnumTipoAnimal tpAnimal){
		List<Racas> racas =null;
		
		try {
			racasRole = getContext().getBean(RacasRole.class);
			racas = racasRole.consultaRacas(tpAnimal);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


	
		return Response.ok(racas).build();

	}
	

	@POST
	@Path("tutor/salvar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvarTutor(Tutor tutor){
		tutorRole = getContext().getBean(TutorRole.class);
		animalRole = getContext().getBean(AnimalRole.class);
		if(tutor.getId()>0){
			try {
				tutorRole.update(tutor);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}
		}else{
			try {
				if(tutor.getAnimal().getId()==0){
					animalRole.insert(tutor.getAnimal());
				}
				tutorRole.insert(tutor);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}

		}
		
		return Response.ok().entity(tutor).build();


	}

}
