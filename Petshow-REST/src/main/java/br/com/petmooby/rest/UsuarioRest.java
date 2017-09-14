package br.com.petmooby.rest;

import java.util.HashMap;
import java.util.Arrays;
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

import br.com.petmooby.enums.EnumFlTpEstabelecimento;
import br.com.petmooby.enums.EnumRoles;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Acesso;
import br.com.petmooby.model.Amigo;
import br.com.petmooby.model.SecurityLogin;
import br.com.petmooby.model.Usuario;
import br.com.petmooby.model.UsuarioCliente;
import br.com.petmooby.role.AcessoRole;
import br.com.petmooby.role.AmigoRole;
import br.com.petmooby.role.SecurityLoginRole;
import br.com.petmooby.role.TutorRole;
import br.com.petmooby.role.UsuarioClienteRole;
import br.com.petmooby.role.UsuarioRole;
import br.com.petmooby.util.KeyNewUserValidateUtil;
import br.com.petmooby.util.RestUtil;

@Component
@Path("/usuario")
public class UsuarioRest extends SuperRestClass{
	
	
	
	UsuarioRole usuarioRole;
	UsuarioClienteRole usuarioClienteRole;
	TutorRole tutorRole;
	AmigoRole amigoRole;
	AcessoRole acessoRole;
	SecurityLoginRole securityLoginRole;

	@GET
	@Path("consulta/like/nome/{idLogado}/{descNome}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response usuarioPorNome(@PathParam("idLogado") long idLogado,@PathParam("descNome") String nome){

		//inicializar();
		List<Usuario> usuarios =null;
		try {
			usuarioRole = getContext().getBean(UsuarioRole.class);
			usuarios = usuarioRole.consultaPorNomeOuAnimal(nome);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(usuarios).build();

	}
	
	
	@POST
	@Path("precadastro")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response preCadastro(Usuario usuario){
		try {
			usuarioRole 		= getContext().getBean(UsuarioRole.class);
			acessoRole  		= getContext().getBean(AcessoRole.class);
			securityLoginRole   = getContext().getBean(SecurityLoginRole.class);
			//-----------------------------------------
			//NOTE: Por padrao usaremos a role ADMIN
			//-----------------------------------------
			Acesso acesso = acessoRole.findAcesso(EnumRoles.ROLE_ADMIN);
			if(usuario.getFlTpEstabelecimento() == EnumFlTpEstabelecimento.PETSHOP
					|| usuario.getFlTpEstabelecimento() == EnumFlTpEstabelecimento.ONG){
				acesso = acessoRole.findAcesso(EnumRoles.ROLE_ADMIN);
			}else{
				acesso = acessoRole.findAcesso(EnumRoles.ROLE_PRIVATE);
			}
			List<Acesso> authorities = Arrays.asList(acesso);
			//-------------------------------------------
			usuario.setAcessos(authorities);
			if(usuario.getCnpjCpf() != null && usuario.getCnpjCpf().trim().isEmpty()){
				usuario.setCnpjCpf("11111111111111");
			}
			usuarioRole.insertPreCadastro(usuario);
			SecurityLogin securityLogin = usuarioRole.genarateSecurityLogin(usuario);
			securityLogin = securityLoginRole.insert(securityLogin);
			usuarioRole.sendEmail(usuario,securityLogin);
			
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(usuario).build();



	}
	
	@POST
	@Path("salvar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(Usuario usuario){

		//inicializar();
		usuarioRole = getContext().getBean(UsuarioRole.class);
		if(usuario.getId()>0){
			try {
				usuarioRole.update(usuario);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}
		}else{
			try {
				usuarioRole.insert(usuario);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}

		}
		
		return Response.ok().entity(usuario).build();


	}

	@POST
	@Path("salvar/usercli")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvarUsuarioCliente(UsuarioCliente usuarioCliente){
		usuarioClienteRole = getContext().getBean(UsuarioClienteRole.class);
		if(usuarioCliente.getId()>0){
			try {
				usuarioClienteRole.insert(usuarioCliente);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().build();
	}

	
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuario(@PathParam("id") long id){

		//inicializar();
		Usuario usuario = new Usuario();
		try {
			usuarioRole = getContext().getBean(UsuarioRole.class);
			usuario = usuarioRole.find(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(usuario).build();

	}
	@GET
	@Path("facebook/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioForFacebookId(@PathParam("id") long id){

		//inicializar();
		Usuario usuario = new Usuario();
		try {
			usuarioRole = getContext().getBean(UsuarioRole.class);
			usuario = usuarioRole.findFacebook(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(usuario).build();

	}
	
	@GET
	@Path("consulta/clientes/{idEstabelecimento}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response clientes(  @PathParam("idEstabelecimento") long idEstabelecimento){

		//inicializar();
		List<Usuario> usuarios =null;
		try {
			usuarioRole = getContext().getBean(UsuarioRole.class);
			usuarios =usuarioRole.listaClientes(idEstabelecimento);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(usuarios).build();

	}

	@GET
	@Path("consulta/clientes/{idEstabelecimento}/{parteNome}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response clientesLike(  @PathParam("idEstabelecimento") long idEstabelecimento,@PathParam("parteNome") String parteNome){

		//inicializar();
		List<Usuario> usuarios =null;
		try {
			usuarioRole = getContext().getBean(UsuarioRole.class);
			usuarios =usuarioRole.listaClientesAutoComplete(idEstabelecimento,parteNome);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(usuarios).build();

	}
	
	@GET
	@Path("consulta/login/{nmLogin}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByLoginName(@PathParam("nmLogin")String nmLogin){
		Usuario usuario =null;
		usuarioRole = getContext().getBean(UsuarioRole.class);
		usuario = usuarioRole.consultaPorNomeLogin(nmLogin);
		if(usuario==null){
			usuario = new Usuario();
		}else{
			if(!usuario.isValidated()){
				usuario = new Usuario();
				return RestUtil.getResponseValidationErro(new ExceptionValidation("Usuario não realizou a sua validação, por favor acesso o seu e-mail e use o link para se validar"));
				
			}
		}
		
		return Response.ok(usuario).build();

	}

	@GET
	@Path("amigos/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserByLoginName(@PathParam("idUsuario")Long idUsuario){
		
		
		amigoRole = getContext().getBean(AmigoRole.class);
		HashMap<String,Object> dados ;
		try {
			dados= amigoRole.getAmigos(idUsuario);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(dados).build();
		
		

	}
	

	@GET
	@Path("amigo/{idUsuario}/{idAmigo}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAmigo(@PathParam("idUsuario")Long idUsuario,@PathParam("idAmigo")Long idAmigo){
		Amigo amigo =null;
		amigoRole = getContext().getBean(AmigoRole.class);
		try {
			amigo = amigoRole.findAmigo(idUsuario, idAmigo);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(amigo).build();

	}
	
	@POST
	@Path("amigo/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response salvar(Amigo amigo){

		amigoRole = getContext().getBean(AmigoRole.class);
		if(amigo.getId()>0){
			try {
				amigoRole.update(amigo);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}
		}else{
			try {
				amigoRole.insert(amigo);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}

		}
		
		return Response.ok().entity(amigo).build();


	}
	
	@DELETE
	@Path("amigo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAmigo(Amigo amigo){
		
		amigoRole = getContext().getBean(AmigoRole.class);
		try {
			amigoRole.delete(amigo.getId());
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(amigo).build();

	}
	
	@POST
	@Path("validate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validate(SecurityLogin securityLogin){
		Usuario usuario = new Usuario();
		long userId = 0L;
		try {
			String key = securityLogin.getKey();
			if(usuarioRole==null){
				usuarioRole = getContext().getBean(UsuarioRole.class);
			}
			String keyParts[] = KeyNewUserValidateUtil.getKeyParts(key);
			if(keyParts==null || keyParts.length < 2){
				return RestUtil.getResponseValidationErro(new ExceptionValidation("Valores invalidos"));
			}else{
				try {
					userId = Long.parseLong(keyParts[1]) ;
				} catch (NumberFormatException e) {
					// TODO: handle exception
					return RestUtil.getResponseValidationErro(new ExceptionValidation(e.getMessage()));
				}
			}
			usuario = usuarioRole.find(userId);
			if(usuario!=null){
				usuario.setValidated(true);
				usuario = usuarioRole.update(usuario);
			}
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(usuario).build();
		
	}

}
