package br.com.petshow.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Bairro;
import br.com.petshow.model.Cidade;
import br.com.petshow.model.Estado;
import br.com.petshow.model.Usuario;
import br.com.petshow.role.AnuncioRole;
import br.com.petshow.role.BairroRole;
import br.com.petshow.role.CidadeRole;
import br.com.petshow.role.EstadoRole;
import br.com.petshow.role.UsuarioRole;
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
	public Response usuarioPorNome(@PathParam("cep") long cep){

		inicializar();
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

		inicializar();
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
	@Path("consulta/cidade/estado/id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response cidadePorEstadoPorID(@PathParam("id") String id){
		System.out.println("trace descobrir lentidao:entrou Restendereco:"+new Date().getTime());
		inicializar();
		
		List<Cidade> cidades =null;
		try {
			System.out.println("trace descobrir lentidao:entrou Restendereco1:"+new Date().getTime());
			cidadeR = getContext().getBean(CidadeRole.class);
			System.out.println("trace descobrir lentidao:entrou Restendereco2:"+new Date().getTime());
			cidades = cidadeR.consultaPorEstadoID(Long.parseLong(id));
			System.out.println("trace descobrir lentidao:entrou Restendereco3:"+new Date().getTime());
			
			
			
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		System.out.println("trace descobrir lentidao:fim Restendereco:"+new Date().getTime());
		return Response.ok(cidades).build();

	}
	
	
	
	@GET
	@Path("consulta/bairro/cidade/{idCidade}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bairroPorCidade(@PathParam("idCidade") long idCidade){

		inicializar();
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

		inicializar();
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
}

