package br.com.petshow.rest;

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
import br.com.petshow.model.Animal;
import br.com.petshow.model.SmartphoneREG;
import br.com.petshow.role.AnimalRole;
import br.com.petshow.role.SmartphoneREGRole;
import br.com.petshow.util.RestUtil;

@Component
@Path("/fcm")
public class FCMRest   extends SuperRestClass{

	
	SmartphoneREGRole smartphoneREGRole;
	@POST
	@Path("salvar/smart")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvar(SmartphoneREG reg){
	
		smartphoneREGRole = getContext().getBean(SmartphoneREGRole.class);
		try {
			SmartphoneREG existente = smartphoneREGRole.findFacebook(reg.getUsuario().getIdFacebook());
			if(existente ==null){
				smartphoneREGRole.deletePorIDFCM(reg.getIdSmartPhoneFCM());
				smartphoneREGRole.insert(reg);
			}else{
				if(!reg.getIdSmartPhoneFCM().equals(existente.getIdSmartPhoneFCM())){
					existente.setIdSmartPhoneFCM(reg.getIdSmartPhoneFCM());
					smartphoneREGRole.update(existente);
				}
			}
			
			
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		}catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(reg).build(); 


	}
	
	@GET
	@Path("smartreg/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaSmartReg(@PathParam("id") long id){
	
		SmartphoneREG entidade=null;
		try {
			smartphoneREGRole = getContext().getBean(SmartphoneREGRole.class);
			entidade=smartphoneREGRole.find(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
	@GET
	@Path("smartreg/facebook/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaSmartRegFacebook(@PathParam("id") long id){
	
		SmartphoneREG entidade=null;
		try {
			smartphoneREGRole = getContext().getBean(SmartphoneREGRole.class);
			entidade=smartphoneREGRole.findFacebook(id);
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}
}
