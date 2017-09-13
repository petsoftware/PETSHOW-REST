package br.com.petmooby.rest;

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

import br.com.petmooby.exceptions.ExceptionNotFoundRecord;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Venda;
import br.com.petmooby.role.VendaRole;
import br.com.petmooby.util.RestUtil;

@Component
@Path("/venda")
public class VendaRest  extends SuperRestClass{
	
	VendaRole vendaR;
	
	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarVenda(Venda venda){
 
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
	@GET
	@Path("consulta/{palavraChave}/{idCidade}/{idEstado}/{limiteRegistros}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaPorFiltro(@PathParam("palavraChave") String palavraChave,
									@PathParam("idCidade") long idCidade,
									@PathParam("idEstado") long idEstado,
									@PathParam("limiteRegistros") int limiteRegistros
									){
		List<Venda> vendas =null;
		try {
			vendaR = getContext().getBean(VendaRole.class);
			vendas = vendaR.consultaVendasFiltros(palavraChave.equals("null")?"":palavraChave, idCidade, idEstado,limiteRegistros);

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(vendas).build();

	}
	
	@GET
	@Path("{idVenda}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaVenda(@PathParam("idVenda") long idVenda){

		Venda vendaConsultada=null;
		try {
			vendaR = getContext().getBean(VendaRole.class);
			vendaConsultada= vendaR.find(idVenda);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(vendaConsultada).build();
	}

}
