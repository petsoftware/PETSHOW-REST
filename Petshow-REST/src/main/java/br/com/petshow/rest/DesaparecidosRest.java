package br.com.petmooby.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import br.com.petmooby.constants.RestConstants;
import br.com.petmooby.constants.RestPathConstants;
import br.com.petmooby.exceptions.ExceptionValidation;
import br.com.petmooby.model.Desaparecidos;
import br.com.petmooby.role.DesaparecidosRole;

@Component
@Path(RestPathConstants.PATH_DESAPARECIDOS)
public class DesaparecidosRest extends SuperRestClass{
	private ApplicationContext context;
	
	private DesaparecidosRole role;
	@POST
	@Path(RestConstants.REST_PATTERN_URL_INSERT)
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Desaparecidos postStudentRecord(Desaparecidos desaparecidos){
		context = new ClassPathXmlApplicationContext("spring-context.xml");
		role = context.getBean(DesaparecidosRole.class);
		try {
			desaparecidos = role.insert(desaparecidos);
			return desaparecidos;
			//return Response.status(200).entity(desaparecidos).build();
		} catch (ExceptionValidation e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return desaparecidos;
			//e.printStackTrace();
			//return getResponseException(e);
		}
		
	}
	
//	@GET
//	@Path("/print/{name}")
//	@Produces("application/json")
//	public List<Desaparecidos> produceJSON( @PathParam("name") String name ) {
//		Student st = new Student(name, "Marco",19,12);	 
//		return st;
//	}
	
	@GET
	@Path(RestConstants.REST_PATTERN_URL_GETALL)
	@Produces(MediaType.APPLICATION_JSON)
	public List<Desaparecidos> findAll() {
		List<Desaparecidos> list = new ArrayList<Desaparecidos>();	 
		return list;
	}


}
