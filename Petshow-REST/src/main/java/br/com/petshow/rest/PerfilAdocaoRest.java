package br.com.petshow.rest;

import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Service;

import br.com.petshow.constants.RestPathConstants;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.PerfilAdocao;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.PerfilAdocaoRole;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.util.RestUtil;
/**
 * 
 * @author Rafael ROcha
 * @since 1.0 11/08/2017 as 15:14
 */
@Service
@Path("/"+RestPathConstants.PATH_PERFIL_ADOCAO)
public class PerfilAdocaoRest extends SuperRestClass{

	PerfilAdocaoRole perfilAdocaoRole;
	UsuarioRole usuarioRole;
	
	
	@POST
	@Path(RestPathConstants.SALVAR)
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvar(PerfilAdocao perfilAdocao){
	
		perfilAdocaoRole = getContext().getBean(PerfilAdocaoRole.class);
		if(perfilAdocao.getId()>0){
			try {
				perfilAdocaoRole.update(perfilAdocao);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				perfilAdocaoRole.insert(perfilAdocao);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(perfilAdocao).build();


	}
	
	/**
	 * 
	 * @param id do usuario
	 * @return
	 */
	@GET
	@Path(RestPathConstants.GET+"/{"+RestPathConstants.USER_ID+"}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPerfilAdocaoPorUsuario(@PathParam(RestPathConstants.USER_ID) long userId){
		Usuario usuario  = null;
		PerfilAdocao perfilAdocao = new PerfilAdocao();
		perfilAdocaoRole = getContext().getBean(PerfilAdocaoRole.class);
		usuarioRole 	 = getContext().getBean(UsuarioRole.class);	
		try {
			//perfilAdocao = perfilAdocaoRole.find(userId);
			usuario		 = usuarioRole.find(userId);
			if(usuario != null){
				perfilAdocao = perfilAdocaoRole.findPerfilByUser(usuario);
			}
		}catch(NoResultException noResult){
			return Response.ok(new PerfilAdocao()).build();
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(perfilAdocao).build();

	}
	
	/**
	 * Numero de animais disponiveis para adocao dentro do perfil de adocao do usuario
	 * @param id do usuario
	 * @return
	 */
	@GET
	@Path(RestPathConstants.GET+"/count/{"+RestPathConstants.USER_ID+"}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCountPerfilAdocaoPorUsuario(@PathParam(RestPathConstants.USER_ID) long userId){
		Usuario usuario  = null;
		perfilAdocaoRole = getContext().getBean(PerfilAdocaoRole.class);
		usuarioRole 	 = getContext().getBean(UsuarioRole.class);	
		long count		 = 0;
		try {
			usuario		 = usuarioRole.find(userId);
			count	 	= 0;
			if(usuario != null){
				count = perfilAdocaoRole.countAdocoesNoPerfil(usuario);
			}
		}catch(NoResultException noResult){
			return Response.ok(new PerfilAdocao()).build();
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(count).build();

	}
}
