package br.com.tarefa.rest;

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

import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.rest.SuperRestClass;
import br.com.petshow.role.AnimalRole;
import br.com.petshow.util.RestUtil;
import br.com.tarefa.model.Tarefa;
import br.com.tarefa.role.TarefaRole;



@Component
@Path("/tarefa")
public class TarefaRest  extends SuperRestClass {

	TarefaRole tarefaRole;
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaTarefa(@PathParam("id") long id){

		Tarefa entidade=null;
		try {
		

			tarefaRole = getContext().getBean(TarefaRole.class);
			entidade= tarefaRole.find(id);

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}


		return Response.ok().entity(entidade).build();
	}


	@POST
	@Path("salvar")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarTarefa(Tarefa tarefa){

	

		tarefaRole = getContext().getBean(TarefaRole.class);
		if(tarefa.getId()>0){
			try {
				tarefaRole.update(tarefa);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}
		}else{
			try {
				tarefaRole.insert(tarefa);

			} catch (ExceptionValidation e) {

				return RestUtil.getResponseValidationErro(e);

			}catch (Exception e) {

				return RestUtil.getResponseErroInesperado(e);

			}

		}
		return Response.ok().entity(tarefa).build();


	}

	@POST
	@Path("status")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response alterarStatus(Tarefa tarefa){

		

		tarefaRole = getContext().getBean(TarefaRole.class);

		try {
			tarefaRole.alterarStatus(tarefa);

		} catch (ExceptionValidation e) {

			return RestUtil.getResponseValidationErro(e);

		}catch (Exception e) {

			return RestUtil.getResponseErroInesperado(e);

		}
		return Response.ok().entity(tarefa).build();


	}

	@GET
	@Path("obs/{id}/{obs}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaCep(@PathParam("id") long id,
			@PathParam("obs") String obs){
		Tarefa tarefa =null;
		try {
			
			tarefaRole = getContext().getBean(TarefaRole.class);	
			tarefa = tarefaRole.find(id);
			tarefa.getObservacao().add(obs);
			tarefaRole.update(tarefa);

		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok().entity(tarefa).build();

	}
	@GET
	@Path("todas")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tarefas(){

		
		List<Tarefa> tarefas =null;
		try {
			tarefaRole = getContext().getBean(TarefaRole.class);
			tarefas = tarefaRole.consultaTarefas();
		} catch (ExceptionValidation e) {
			return RestUtil.getResponseValidationErro(e);
		} catch (Exception e) {
			return RestUtil.getResponseErroInesperado(e);
		}
		return Response.ok(tarefas).build();

	}

}
