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

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import br.com.petshow.constants.RestConstants;
import br.com.petshow.exceptions.ExceptionNotFoundRecord;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Anuncio;
import br.com.petshow.model.Desaparecidos;
import br.com.petshow.role.AnuncioRole;
import br.com.petshow.role.DesaparecidosRole;

@Component
@Path("/anuncio")
public class AnuncioRest extends SuperRestClass{


	AnuncioRole anuncioR;

	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarAnuncio(Anuncio anuncio){

		inicializar();

		anuncioR = getContext().getBean(AnuncioRole.class);
		if(anuncio.getId()>0){
			try {
				anuncioR.update(anuncio);

			} catch (ExceptionValidation e) {

				e.printStackTrace();

			}catch (Exception e) {

				e.printStackTrace();

			}
		}else{
			try {
				anuncioR.insert(anuncio);

			} catch (ExceptionValidation e) {

				e.printStackTrace();

			}catch (Exception e) {

				e.printStackTrace();

			}

		}
		return Response.ok().entity(anuncio).build();


	}



	@GET
	@Path("consulta/usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAnunciosPorUsuario(@PathParam("idUsuario") long idUsuario){

		inicializar();
		try {
			anuncioR = getContext().getBean(AnuncioRole.class);
			List<Anuncio> anuncios = anuncioR.consultaPorUsuario(idUsuario);
			return Response.ok(anuncios).build();


		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@DELETE
	@Path("{idAnuncio}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("idAnuncio") long idAnuncio){

		inicializar();


		try {
			anuncioR = getContext().getBean(AnuncioRole.class);
			anuncioR.delete(idAnuncio);
		} catch (ExceptionValidation e) {

			e.printStackTrace();
		} catch (ExceptionNotFoundRecord e) {
			return Response.status(Response.Status.NOT_FOUND).entity("Anuncio com o id "+idAnuncio+" não existe!").build();

		}catch (Exception e) {

			e.printStackTrace();
		}



		return Response.ok().build();
	}

	@GET
	@Path("{idAnuncio}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAnuncio(@PathParam("idAnuncio") long idUsuario){

		Anuncio anuncioConsultado=null;
		try {
			inicializar();

			anuncioR = getContext().getBean(AnuncioRole.class);
			anuncioConsultado=anuncioR.find(idUsuario);

		} catch (ExceptionValidation e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}


		return Response.ok().entity(anuncioConsultado).build();
	}

}
