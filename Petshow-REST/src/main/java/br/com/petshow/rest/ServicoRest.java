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

import br.com.petshow.exceptions.ExceptionNotFoundRecord;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Servico;
import br.com.petshow.role.ServicoRole;
import br.com.petshow.util.RestUtil;

@Component
@Path("/servico")
public class ServicoRest extends SuperRestClass{


	ServicoRole servicoRole;

	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarServico(Servico servico){
		servicoRole = getContext().getBean(ServicoRole.class);
		if(servico.getId()>0){
			try {
				servicoRole.update(servico);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				servicoRole.insert(servico);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}

		}
		return Response.ok().entity(servico).build();


	}



	@GET
	@Path("consulta/usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaServicosPorUsuario(@PathParam("idUsuario") long idUsuario){

		List<Servico> servicos =null;
		try {
			servicoRole = getContext().getBean(ServicoRole.class);
			servicos = servicoRole.consultaPorUsuario(idUsuario);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(servicos).build();

	}

	@DELETE
	@Path("{idServico}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("idServico") long idServico){
		try {
			servicoRole = getContext().getBean(ServicoRole.class);
			servicoRole.delete(idServico);
			
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
	@Path("{idServico}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaServico(@PathParam("idServico") long idUsuario){

		Servico servicoConsultado=null;
		try {
			servicoRole = getContext().getBean(ServicoRole.class);
			servicoConsultado=servicoRole.find(idUsuario);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(servicoConsultado).build();
	}

}
