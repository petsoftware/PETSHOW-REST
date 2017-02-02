package br.com.petshow.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Adocao;
import br.com.petshow.model.Venda;
import br.com.petshow.role.AdocaoRole;
import br.com.petshow.role.VendaRole;
import br.com.petshow.util.RestUtil;


@Component
@Path("/adocao")
public class AdocaoRest extends SuperRestClass{
	//private static ApplicationContext context;
	AdocaoRole adocaoRole;
	@GET
	@Path("gravar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response doPost(String json){
		//context = new ClassPathXmlApplicationContext("spring-context.xml");
//		adocaoRole = getContext().getBean(AdocaoRole.class);
//		Adocao adocao = new Adocao();
//		adocao.setDataAdocao(new Date());
		
		try {
//			adocaoRole.insert(adocao);
			return Response.ok("OK").build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
	}
	
	@POST
	@Path("post")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postStudentRecord(Adocao adocao){
		System.out.println("Adocao post");
		//String result = "Record entered: "+ adocao;
		return Response.status(200).entity(adocao.toString()).build();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaAdocao(@PathParam("id") long id){

		Adocao entidade=null;
		try {
			inicializar();

			adocaoRole = getContext().getBean(AdocaoRole.class);
			entidade= adocaoRole.find(id);

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
	


}
