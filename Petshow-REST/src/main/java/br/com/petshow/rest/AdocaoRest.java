package br.com.petshow.rest;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;

import br.com.petshow.model.Adocao;
import br.com.petshow.role.AdocaoRole;


@Component
@Path("/Adocao")
public class AdocaoRest extends SuperRestClass{
	//private static ApplicationContext context;
	private static AdocaoRole adocaoRole;
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


}
