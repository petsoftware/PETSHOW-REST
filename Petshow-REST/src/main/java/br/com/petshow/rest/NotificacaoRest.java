package br.com.petshow.rest;

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

import br.com.petshow.enums.EnumAssuntoNotificacao;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Notificacao;
import br.com.petshow.model.Perdido;
import br.com.petshow.model.Servico;
import br.com.petshow.model.Usuario;
import br.com.petshow.objects.query.NotificacaoQuery;
import br.com.petshow.role.AdocaoRole;
import br.com.petshow.role.AnimalRole;
import br.com.petshow.role.NotificacaoRole;
import br.com.petshow.role.PerdidoRole;
import br.com.petshow.role.ServicoRole;
import br.com.petshow.role.SmartphoneREGRole;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.util.MensagemUtil;
import br.com.petshow.util.RestUtil;


@Component
@Path("/notificacao")
public class NotificacaoRest  extends SuperRestClass{
	
	
	NotificacaoRole notificacaoRole;
	SmartphoneREGRole smartRegRole;
	private PerdidoRole perdidoRole;
	private UsuarioRole usuarioRole;

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
			String aviso = "Sr."+usuarioDestinatario.getNome()+", "+(MensagemUtil.getVogalSexo(animal))+" "+animal.getNome()+" já está pronto "+(MensagemUtil.getVogalSexo(animal))+" para ser entregue!";
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
		try {
			perdidoRole 	= getContext().getBean(PerdidoRole.class);
			notificacaoRole = getContext().getBean(NotificacaoRole.class);
			usuarioRole		= getContext().getBean(UsuarioRole.class);
			String mensagem	= parametros.get("mensagem");
			String telefone	= parametros.get("telefone");
			String email	= parametros.get("email");
			String nome		= parametros.get("nome");
			String userRemet= parametros.get("idUserRemet");
			Usuario remetent= obterUsuarioRemetenteSeExistir(userRemet);
			Perdido  perdido = perdidoRole.find(Long.parseLong(parametros.get("idPerdido")));
			Notificacao notificacao = new Notificacao();
			notificacao.setDtNotificacao(new Date());
			notificacao.setFlEnviada(false);
			notificacao.setFlExcluida(false);
			notificacao.setFlLida(false);
			notificacao.setMsgNotificacao(mensagem);
			notificacao.setEmail(email);
			notificacao.setContato(telefone);
			notificacao.setAssuntoNotificacao(EnumAssuntoNotificacao.PERDIDO);
			notificacao.setUsuarioDestinatario(perdido.getUsuario());
			notificacao.setNome(nome);
			notificacao.setUsuarioRemetente(remetent);
			notificacaoRole.insert(notificacao);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
	}
	
	@POST
	@Path("responder")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response responderNotificacao(NotificacaoQuery notificacaoQuery){	
		try {
			perdidoRole 	= getContext().getBean(PerdidoRole.class);
			notificacaoRole = getContext().getBean(NotificacaoRole.class);
			usuarioRole		= getContext().getBean(UsuarioRole.class);
			Notificacao notificacao = new Notificacao();
			notificacao.setDtNotificacao(new Date());
			notificacao.setFlEnviada(false);
			notificacao.setFlExcluida(false);
			notificacao.setFlLida(false);
			notificacao.setMsgNotificacao(notificacaoQuery.getMensagem());
			notificacao.setEmail(notificacaoQuery.getEmail());
			notificacao.setContato(notificacaoQuery.getTelefone());
			notificacao.setAssuntoNotificacao(notificacaoQuery.getAssunto());
			notificacao.setUsuarioDestinatario(notificacaoQuery.getDestinatario());
			notificacao.setNome(notificacaoQuery.getNome());
			notificacao.setUsuarioRemetente(notificacaoQuery.getRemetente());
			notificacaoRole.insert(notificacao);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().build();
	}


	private Usuario obterUsuarioRemetenteSeExistir(String userRemet) throws ExceptionValidation {
		Usuario remetent = null;
		if(userRemet != null){
			if(!userRemet.trim().isEmpty() && !userRemet.equals("0")){
				remetent = usuarioRole.find(Long.parseLong(userRemet));
			}
		}
		return remetent;
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
	@GET
	@Path("usuario/count/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response countNotificacaoPorUsuario(@PathParam("idUsuario") long idUsuario){
		long count = 0;
		try {
			notificacaoRole= getContext().getBean(NotificacaoRole.class);
			count = notificacaoRole.countNotificacaoDoUsuario(idUsuario);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(count).build();

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
				notificacaoRole.avisarPorEmailUmaNotificacao(notificacao);
			} catch (ExceptionValidation e) {
				return RestUtil.getResponseValidationErro(e);
			}catch (Exception e) {
				return RestUtil.getResponseErroInesperado(e);
			}
		}
		return Response.ok().entity(notificacao).build();
	}
	
	@GET
	@Path("get/{idNotificacao}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificacao(@PathParam("idNotificacao") long idNotificacao){
		Notificacao notificacao = null;
		try {
			notificacaoRole	= getContext().getBean(NotificacaoRole.class);
			notificacao 	= notificacaoRole.find(idNotificacao);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(notificacao).build();
	}

}
