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
import br.com.petshow.model.Adocao;
import br.com.petshow.model.PerfilAdocao;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.AdocaoRole;
import br.com.petshow.role.PerfilAdocaoRole;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.util.RestUtil;


@Component
@Path("/adocao")
public class AdocaoRest extends SuperRestClass{
	AdocaoRole adocaoRole;
	private UsuarioRole usuarioRole;
	private PerfilAdocaoRole perfilAdocaoRole;
	public AdocaoRest() {
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAdocao(@PathParam("id") long id){
		Adocao entidade=null;
		try {
			adocaoRole = getContext().getBean(AdocaoRole.class);
			entidade= adocaoRole.find(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
	
	@POST
	@Path("salvar")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvar(Adocao adocao){
	
		adocaoRole = getContext().getBean(AdocaoRole.class);
		if(adocao.getId()>0){
			try {
				adocaoRole.update(adocao);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				adocaoRole.insert(adocao);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(adocao).build();
	}
	
	@GET
	@Path("consulta/usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response adocaoPorUsuario(@PathParam("idUsuario") long idUsuario){
		List<Adocao> animais =null;
		try {
			adocaoRole = getContext().getBean(AdocaoRole.class);
			animais = adocaoRole.consultaPorUsuario(idUsuario);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(animais).build();
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id){
		try {
			adocaoRole = getContext().getBean(AdocaoRole.class);
			adocaoRole.delete(id);
			
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
	@Path("consulta/perfil/usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response adocoesDisponiveisPorPerfil(@PathParam("idUsuario") long idUsuario){
		List<Adocao> animais 		= null;
		PerfilAdocao perfilAdocao	= null;
		try {
			usuarioRole 	= getContext().getBean(UsuarioRole.class);
			perfilAdocaoRole= getContext().getBean(PerfilAdocaoRole.class); 
			Usuario usuario = usuarioRole.find(idUsuario);
			if(usuario != null && usuario.getId() > 0){
				perfilAdocao = perfilAdocaoRole.findPerfilByUser(usuario);
				if(perfilAdocao != null){
					animais = perfilAdocaoRole.findAdocoesByPerfil(perfilAdocao);
				}
			}
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(animais).build();
	}
	
	@GET
	@Path("/usuario/adocoes/anunciadas/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAdocoesAnunciadasUsuario(@PathParam("id") long idUsuario){
		List<Adocao> entidade=null;
		try {
			adocaoRole = getContext().getBean(AdocaoRole.class);
			entidade= adocaoRole.consultaPorUsuario(idUsuario);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
	
}
