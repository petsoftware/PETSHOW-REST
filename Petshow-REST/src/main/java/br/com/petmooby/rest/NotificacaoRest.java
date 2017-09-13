package br.com.petmooby.rest;

import java.util.Date;
import java.util.HashMap;
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

import com.wedevol.xmpp.NotificationSend;

import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Adocao;
import br.com.petmooby.model.Animal;
import br.com.petmooby.model.Notificacao;
import br.com.petmooby.model.Perdido;
import br.com.petmooby.model.Servico;
import br.com.petmooby.model.Usuario;
import br.com.petmooby.role.AdocaoRole;
import br.com.petmooby.role.AnimalRole;
import br.com.petmooby.role.NotificacaoRole;
import br.com.petmooby.role.PerdidoRole;
import br.com.petmooby.role.ServicoRole;
import br.com.petmooby.role.SmartphoneREGRole;
import br.com.petmooby.role.UsuarioRole;
import br.com.petmooby.util.MensagemUtil;
import br.com.petmooby.util.RestUtil;


@Component
@Path("/notificacao")
public class NotificacaoRest  extends SuperRestClass{
	
	
	NotificacaoRole notificacaoRole;
	SmartphoneREGRole smartRegRole;

	@POST
	@Path("entrega")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response notificacaoAviso(HashMap<String,String> parametros){  
		//@QueryParam("idUsuario")long idUsuario,@QueryParam("idAnimal")long idAnimal
		try {
			
			notificacaoRole= getContext().getBean(NotificacaoRole.class);
			smartRegRole= getContext().getBean(SmartphoneREGRole.class);
			
			Usuario  usuarioDestinatario = getContext().getBean(UsuarioRole.class).find(Long.parseLong(parametros.get("idUsuarioDestinatario")));
			Usuario  usuarioRemetente = getContext().getBean(UsuarioRole.class).find(Long.parseLong(parametros.get("idUsuarioRemetente")));
			Animal animal =  getContext().getBean(AnimalRole.class).find(Long.parseLong(parametros.get("idAnimal")));
			Servico servico = getContext().getBean(ServicoRole.class).find(Long.parseLong(parametros.get("idServico")));
			String aviso = "Sr."+usuarioDestinatario.getNome()+", "+(MensagemUtil.getVogalSexo(animal))+" "+animal.getNome()+" j� est� pront"+(MensagemUtil.getVogalSexo(animal))+" para ser entregue!";
			
			Notificacao notificacao =   new Notificacao();
			notificacao.setDtNotificacao(new Date());
			notificacao.setTpNotificacao("E");
			notificacao.setUsuarioDestinatario(usuarioDestinatario);
			notificacao.setUsuarioRemetente(usuarioRemetente);
			notificacao.setMsgNotificacao(aviso);
			notificacaoRole.insert(notificacao);
			
			
			
			NotificationSend.sendNotificationAndroid(smartRegRole.findFacebook(usuarioDestinatario.getIdFacebook()).getIdSmartPhoneFCM(), aviso);
			
			
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
	
	@GET
	@Path("usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response notificacaoPorUsuario(@PathParam("idUsuario") long idUsuario){
		List<Notificacao> notificacoes =null;
		try {
			notificacaoRole= getContext().getBean(NotificacaoRole.class);
			notificacoes = notificacaoRole.consultaPorUsuario(idUsuario);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(notificacoes).build();

	}
	
	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvar(Notificacao notificacao){
	
		notificacaoRole= getContext().getBean(NotificacaoRole.class);
		if(notificacao.getId()>0){
			try {
				notificacaoRole.update(notificacao);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}else{
			try {
				notificacaoRole.insert(notificacao);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(notificacao).build();


	}

}
