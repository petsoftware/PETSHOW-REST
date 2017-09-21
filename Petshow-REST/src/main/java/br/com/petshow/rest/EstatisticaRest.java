package br.com.petshow.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.model.Estatistica;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.AdocaoRole;
import br.com.petshow.role.AnuncioRole;
import br.com.petshow.role.ServicoRole;
import br.com.petshow.role.UsuarioRole;
import br.com.petshow.role.VendaRole;
import br.com.petshow.util.RestUtil;

@Component
@Path("/estatistica")
public class EstatisticaRest extends SuperRestClass{

	public EstatisticaRest() {
	}
	private VendaRole vendaRole;
	private ServicoRole servicoRole;
	private AdocaoRole adocaoRole;
	private UsuarioRole usuarioRole;
	private AnuncioRole anuncioRole;
	
	public VendaRole getVendaRole() {
		return vendaRole;
	}

	public void setVendaRole(VendaRole vendaRole) {
		this.vendaRole = vendaRole;
	}

	public ServicoRole getServicoRole() {
		return servicoRole;
	}

	public void setServicoRole(ServicoRole servicoRole) {
		this.servicoRole = servicoRole;
	}

	public AdocaoRole getAdocaoRole() {
		return adocaoRole;
	}

	public void setAdocaoRole(AdocaoRole adocaoRole) {
		this.adocaoRole = adocaoRole;
	}

	@GET
	@Path("usuario/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAdocao(@PathParam("id") long id){
		Estatistica estatistica = new Estatistica();
		Usuario usuario = null;
		try {
			adocaoRole = getContext().getBean(AdocaoRole.class);
			vendaRole  = getContext().getBean(VendaRole.class);
			servicoRole= getContext().getBean(ServicoRole.class);
			usuarioRole= getContext().getBean(UsuarioRole.class);
			anuncioRole= getContext().getBean(AnuncioRole.class);
			usuario = usuarioRole.find(id);
			if(usuario != null){
				long numeroDeAnunciosAtivos		= anuncioRole.numeroDeAnunciosFeitos(usuario);
				long numeroDeVendasClassificadas	= vendaRole.numeroDeVendasRegistradasNoSistema(usuario);
				long numeroDeServicosAtivos		= servicoRole.numeroDeServicosAtivos(usuario);
	
				estatistica.setNumeroDeAnuncios(numeroDeAnunciosAtivos);
				estatistica.setNumeroDeServicos(numeroDeServicosAtivos);
				estatistica.setNumeroDeVendas(numeroDeVendasClassificadas);
			}
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(estatistica).build();
	}
	
}
