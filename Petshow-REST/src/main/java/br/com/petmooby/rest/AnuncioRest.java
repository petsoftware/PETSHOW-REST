package br.com.petmooby.rest;

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

import br.com.petmooby.exceptions.ExceptionNotFoundRecord;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Anuncio;
import br.com.petmooby.role.AnuncioRole;
import br.com.petmooby.util.RestUtil;

@Component
@Path("/anuncio")
public class AnuncioRest extends SuperRestClass{


	AnuncioRole anuncioR;

	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarAnuncio(Anuncio anuncio){
 
		anuncioR = getContext().getBean(AnuncioRole.class);
		if(anuncio.getId()>0){
			try {
				anuncioR.update(anuncio);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}
		}else{
			try {
				anuncioR.insert(anuncio);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}

		}
		return Response.ok().entity(anuncio).build();


	}



	@GET
	@Path("consulta/usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAnunciosPorUsuario(@PathParam("idUsuario") long idUsuario){

		List<Anuncio> anuncios =null;
		try {
			anuncioR = getContext().getBean(AnuncioRole.class);
			anuncios = anuncioR.consultaPorUsuario(idUsuario);

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(anuncios).build();

	}

	@DELETE
	@Path("{idAnuncio}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("idAnuncio") long idAnuncio){
		try {
			anuncioR = getContext().getBean(AnuncioRole.class);
			anuncioR.delete(idAnuncio);
			
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
	@Path("{idAnuncio}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAnuncio(@PathParam("idAnuncio") long idUsuario){

		Anuncio anuncioConsultado=null;
		try {
			anuncioR = getContext().getBean(AnuncioRole.class);
			anuncioConsultado=anuncioR.find(idUsuario);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(anuncioConsultado).build();
	}

}
