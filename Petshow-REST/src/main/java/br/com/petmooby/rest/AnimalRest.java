package br.com.petmooby.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
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

import br.com.petmooby.enums.EnumTipoAnimal;
import br.com.petmooby.exceptions.ExceptionNotFoundRecord;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Adocao;
import br.com.petmooby.model.Animal;
import br.com.petmooby.model.Perdido;
import br.com.petmooby.model.Racas;
import br.com.petmooby.model.Tratamento;
import br.com.petmooby.model.Tutor;
import br.com.petmooby.model.Vacina;
import br.com.petmooby.model.Vermifugo;
import br.com.petmooby.objects.query.AdocaoQuery;
import br.com.petmooby.role.AdocaoRole;
import br.com.petmooby.role.AnimalRole;
import br.com.petmooby.role.PerdidoRole;
import br.com.petmooby.role.RacasRole;
import br.com.petmooby.role.TratamentoRole;
import br.com.petmooby.role.TutorRole;
import br.com.petmooby.role.VacinaRole;
import br.com.petmooby.role.VermifugoRole;
import br.com.petmooby.util.RestUtil;


@Component
@Path("/animal")
public class AnimalRest  extends SuperRestClass{


	AnimalRole animalRole;
	AdocaoRole adocaoRole;	
	PerdidoRole perdidoRole;
	RacasRole racasRole;
	TutorRole tutorRole;
	VacinaRole vacinaRole;
	TratamentoRole tratamentoRole;
	VermifugoRole vermifugoRole;


	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed({"ADMIN"})
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
				Tutor tutor = animal.getTutor();
				if(tutor != null){
					animalRole.insert(animal, tutor);
					animal.setTutor(null);
				}else{
					animalRole.insert(animal);
				}
				
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(animal).build();


	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id){
		try {
			animalRole = getContext().getBean(AnimalRole.class);
			animalRole.delete(id);
			
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
	
	@POST
	@Path("consulta/adocao")
	@Produces(MediaType.APPLICATION_JSON)
	public Response animaisParaAdocao(AdocaoQuery query){

		List<Adocao> animais =null;
		try {
			adocaoRole = getContext().getBean(AdocaoRole.class);
			animais = adocaoRole.consultarAnimaisDisponíveisParaAdocao(query.getEstado(), 
					query.getCidade(), 
					query.getTpAnimal(), 
					query.getFase(), 
					query.getSexo(), 15);
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
	
	@POST
	@Path("perdido/salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvar(Perdido perdido){
	
		perdidoRole = getContext().getBean(PerdidoRole.class);
		if(perdido.getId()>0){
			try {
				perdidoRole.update(perdido);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				perdidoRole.insert(perdido);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(perdido).build();


	}
	
	
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
	
	@DELETE
	@Path("perdido/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePerdido(@PathParam("id") long id){
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
	
	
	@POST
	@Path("tratamento/salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarTratamento(Tratamento tratamento){
	
		tratamentoRole = getContext().getBean(TratamentoRole.class);
		if(tratamento.getId()>0){
			try {
				tratamentoRole.update(tratamento);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				tratamentoRole.insert(tratamento);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(tratamento).build();


	}
	
	@GET
	@Path("/tratamento/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaTratamento(@PathParam("id") long id){

		Tratamento entidade=null;
		try {
			tratamentoRole = getContext().getBean(TratamentoRole.class);
			entidade= tratamentoRole.find(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
	
	
	@DELETE
	@Path("/tratamento/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteTratamento(@PathParam("id") long id){
		try {
			tratamentoRole = getContext().getBean(TratamentoRole.class);
			tratamentoRole.delete(id);
			
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
	@Path("tratamento/animal/{idAnimal}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tratamentoPorAnimal(@PathParam("idAnimal") long idAnimal){
		List<Tratamento> lista =null;
		try {
			tratamentoRole = getContext().getBean(TratamentoRole.class);
			lista = tratamentoRole.consultaPorAnimal(idAnimal);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(lista).build();

	}
	
	
	
	@POST
	@Path("vacina/salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarVacina(Vacina vacina){
	
		vacinaRole = getContext().getBean(VacinaRole.class);
		if(vacina.getId()>0){
			try {
				vacinaRole.update(vacina);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				vacinaRole.insert(vacina);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(vacina).build();


	}
	
	
	@GET
	@Path("/vacina/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaVacina(@PathParam("id") long id){

		Vacina entidade=null;
		try {
			vacinaRole = getContext().getBean(VacinaRole.class);
			entidade= vacinaRole.find(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
	
	
	@DELETE
	@Path("/vacina/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteVacina(@PathParam("id") long id){
		try {
			vacinaRole = getContext().getBean(VacinaRole.class);
			vacinaRole.delete(id);
			
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
	@Path("vacina/animal/{idAnimal}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vacinaPorAnimal(@PathParam("idAnimal") long idAnimal){
		List<Vacina> lista =null;
		try {
			vacinaRole = getContext().getBean(VacinaRole.class);
			lista = vacinaRole.consultaPorAnimal(idAnimal);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(lista).build();

	}
	
	
	
	@POST
	@Path("vermifugo/salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarVermifugo(Vermifugo vermifugo){
	
		vermifugoRole = getContext().getBean(VermifugoRole.class);
		if(vermifugo.getId()>0){
			try {
				vermifugoRole.update(vermifugo);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				vermifugoRole.insert(vermifugo);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(vermifugo).build();


	}
	
	
	@GET
	@Path("/vermifugo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaVermifugo(@PathParam("id") long id){

		Vermifugo entidade=null;
		try {
			vermifugoRole = getContext().getBean(VermifugoRole.class);
			entidade= vermifugoRole.find(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
	
	@DELETE
	@Path("/vermifugo/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteVermifugo(@PathParam("id") long id){
		try {
			vermifugoRole = getContext().getBean(VermifugoRole.class);
			vermifugoRole.delete(id);
			
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
	@Path("vermifugo/animal/{idAnimal}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaVermifugoPorAnimal(@PathParam("idAnimal") long idAnimal){

		Vermifugo entidade=null;
		try {
			vermifugoRole = getContext().getBean(VermifugoRole.class);
			entidade= vermifugoRole.consultaPorAnimal(idAnimal);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
	
	

}
