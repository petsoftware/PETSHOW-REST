package br.com.petshow.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Perdido;
import br.com.petshow.model.Servico;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.AdocaoRole;
import br.com.petshow.role.AnimalRole;
import br.com.petshow.role.PerdidoRole;
import br.com.petshow.role.ServicoRole;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.util.MensagemUtil;
import br.com.petshow.util.RestUtil;


@Component
@Path("/notificacao")
public class NotificacaoRest  extends SuperRestClass{
	
	
	// criada da forma menos generica para dificultar chamadas clandestinas ao rest

	@POST
	@Path("entrega")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response notificacaoAviso(HashMap<String,String> parametros){
		//@QueryParam("idUsuario")long idUsuario,@QueryParam("idAnimal")long idAnimal
		try {
			
			Usuario  usuario = getContext().getBean(UsuarioRole.class).find(Long.parseLong(parametros.get("idUsuario")));
			Animal animal =  getContext().getBean(AnimalRole.class).find(Long.parseLong(parametros.get("idAnimal")));
			Servico servico = getContext().getBean(ServicoRole.class).find(Long.parseLong(parametros.get("idServico")));
			String aviso = "Sr."+usuario.getNome()+", "+(MensagemUtil.getVogalSexo(animal))+" "+animal.getNome()+" j� est� pront"+(MensagemUtil.getVogalSexo(animal))+" para ser entregue!";
	

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
		
	}
	
	
	@POST
	@Path("adocao/msganuncio")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response notificacaoAnuncioAdocao(HashMap<String,String> parametros){
		//@QueryParam("idUsuario")long idUsuario,@QueryParam("idAnimal")long idAnimal
		
		try {
			String mensagem=parametros.get("mensagem");
			String telefone=parametros.get("telefone");
			String email=parametros.get("email");
			
			Adocao  adocao = getContext().getBean(AdocaoRole.class).find(Long.parseLong(parametros.get("idAdocao")));
				
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
		
	}
	
	
	@POST
	@Path("perdido/msganuncio")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response notificacaoAnuncioPerdido(HashMap<String,String> parametros){
		//@QueryParam("idUsuario")long idUsuario,@QueryParam("idAnimal")long idAnimal
		
		try {
			String mensagem=parametros.get("mensagem");
			String telefone=parametros.get("telefone");
			String email=parametros.get("email");
			
			Perdido  perdido = getContext().getBean(PerdidoRole.class).find(Long.parseLong(parametros.get("idPerdido")));

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
		
	}

}