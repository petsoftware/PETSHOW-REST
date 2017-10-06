package br.com.tarefa.rest;

import java.util.Date;
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

import br.com.petshow.enums.EnumAchadoPerdido;
import br.com.petshow.enums.EnumSexo;
import br.com.petshow.exceptions.ExceptionValidation;
import br.com.petshow.model.Animal;
import br.com.petshow.model.Perdido;
import br.com.petshow.rest.SuperRestClass;
import br.com.petshow.role.AnimalRole;
import br.com.petshow.role.PerdidoRole;
import br.com.petshow.util.RestUtil;
import br.com.tarefa.model.Tarefa;
import br.com.tarefa.role.TarefaRole;



@Component
@Path("/teste")
public class TestCargaRest  extends SuperRestClass {

	TarefaRole tarefaRole;
	PerdidoRole perdidoRole;
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
	@Path("carga/perdido")
	@Produces("application/json")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response salvarTarefa(Tarefa tarefa){

	

		perdidoRole = getContext().getBean(PerdidoRole.class);
		try {
			Perdido perdido = perdidoRole.find(1L);
			if(perdido != null){
				for (int i = 0; i < 4; i++) {
					Perdido perdido2 = new Perdido();
					perdido2.setDataCadastro(new Date());
					perdido2.setDddCelular(0);
					perdido2.setDddResidencial(0);
					perdido2.setDescAcontecimento("Perdido teste de carga " + i);
					perdido2.setFlAcontecimento(EnumAchadoPerdido.PERDIDO);
					perdido2.setFlAtivo(true);
					perdido2.setFlSexo(EnumSexo.MACHO);
					perdido2.setFotos(perdido.getFotos());
					perdido2.setNome("Id na posição " + i);
					perdidoRole.insert(perdido2);
				}
			}
		} catch (ExceptionValidation e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
