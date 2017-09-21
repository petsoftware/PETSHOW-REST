package br.com.petshow.rest;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.enums.EnumUF;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.role.BairroRole;
import br.com.petshow.role.CidadeRole;
import br.com.petshow.role.EstadoRole;
import br.com.petshow.util.RestUtil;
@Component
@Path("/endereco")
public class EnderecoRest extends SuperRestClass{
	
	CidadeRole cidadeR;
	BairroRole bairroR;
	EstadoRole estadoR;
	
	@GET
	@Path("consulta/Cep/{cep}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaCep(@PathParam("cep") long cep){

		HashMap<String,String> map =null;
		//try {
			map = new HashMap<String,String>();
			map.put("rua",    "rua do cep");
			map.put("bairro", "bairro do cep");
			map.put("cidade", "cidade do cep");
			map.put("estado", "estado do cep");
			
//			
//		} catch (ExceptionValidation e) {
//			return RestUtil.getResponseValidationErro(e);
//		} catch (Exception e) {
//			return RestUtil.getResponseErroInesperado(e);
//		}
		return Response.ok(map).build();

	}
	
	
	
	@GET
	@Path("consulta/cidade/estado/uf/{uf}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cidadePorEstadoPorUf(@PathParam("uf") String uf){
		List<Cidade> cidades =null;
		try {
			cidadeR = getContext().getBean(CidadeRole.class);
			cidades = cidadeR.consultaPorEstadoUF(uf);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(cidades).build();

	}
	
	@GET
	@Path("consulta/cidade/uf/{uf}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cidadePorEstadoPorUf(@PathParam("uf") EnumUF uf){
		List<Cidade> cidades =null;
		try {
			cidadeR = getContext().getBean(CidadeRole.class);
			cidades = cidadeR.findAllByUF(uf);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(cidades).build();
	}
	
	@GET
	@Path("consulta/cidade/estado/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cidadePorEstadoPorID(@PathParam("id") String id){
		List<Cidade> cidades =null;
		try {
			cidadeR = getContext().getBean(CidadeRole.class);
			cidades = cidadeR.consultaPorEstadoID(Long.parseLong(id));
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(cidades).build();

	}
	
	
	
	@GET
	@Path("consulta/bairro/cidade/{idCidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bairroPorCidade(@PathParam("idCidade") long idCidade){
		List<Bairro> bairros =null;
		try {
			bairroR = getContext().getBean(BairroRole.class);
			bairros= bairroR.consultaPorCidade(idCidade);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(bairros).build();

	}
	@GET
	@Path("consulta/estados")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstados(){

		List<Estado> estados =null;
		try {
			estadoR = getContext().getBean(EstadoRole.class);
			estados= estadoR.getEstados();
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(estados).build();

	}
	
	@GET
	@Path("consulta/bairro/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBairro(@PathParam("id") long id){

		
		Bairro bairro =null;
		try {
			bairroR = getContext().getBean(BairroRole.class);
			bairro= bairroR.find(id);
			
			
			
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(bairro).build();

	}
	
	@GET
	@Path("consulta/cidade/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCidade(@PathParam("id") long id){

		Cidade cidade =null;
		try {
			cidadeR = getContext().getBean(CidadeRole.class);
			cidade= cidadeR.find(id);
			
			
			
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(cidade).build();

	}
	
	@GET
	@Path("consulta/estado/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstado(@PathParam("id") long id){

		
		Estado estado =null;
		try {
			estadoR = getContext().getBean(EstadoRole.class);
			estado= estadoR.find(id);
			
			
			
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(estado).build();

	}
}

