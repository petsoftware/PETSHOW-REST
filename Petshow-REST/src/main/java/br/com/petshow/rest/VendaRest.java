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
import br.com.petshow.model.Anuncio;
import br.com.petshow.model.Venda;
import br.com.petshow.role.AnuncioRole;
import br.com.petshow.role.VendaRole;
import br.com.petshow.util.RestUtil;

@Component
@Path("/venda")
public class VendaRest  extends SuperRestClass{
	
	VendaRole vendaR;
	
	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarVenda(Venda venda){
 
		inicializar();

		vendaR = getContext().getBean(VendaRole.class);
		if(venda.getId()>0){
			try {
				vendaR.update(venda);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}
		}else{
			try {
				vendaR.insert(venda);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}

		}
		return Response.ok().entity(venda).build();


	}
	
	
	@GET
	@Path("consulta/usuario/{idUsuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaVendasPorUsuario(@PathParam("idUsuario") long idUsuario){

		inicializar();
		List<Venda> vendas =null;
		try {
			vendaR = getContext().getBean(VendaRole.class);
			vendas = vendaR.consultaPorUsuario(idUsuario);

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(vendas).build();

	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id){

		inicializar();

		try {
			vendaR = getContext().getBean(VendaRole.class);
			vendaR.delete(id);
			
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (ExceptionNotFoundRecord e) {
			return RestUtil.getResponseValidationErro(e.getMessage());
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}

		return Response.ok().build();
	}


}
