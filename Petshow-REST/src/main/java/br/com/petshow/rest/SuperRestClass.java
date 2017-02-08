package br.com.petshow.rest;

import java.util.Date;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.petshow.util.WriteConsoleUtil;

/**
 * Super Classe para reutilizar metodos nas outras classes REST
 * @author Rafael Rocha
 * @since versao 1.0 - dia 02/12/2016 as 09:42.
 */
public class SuperRestClass {


	private static ApplicationContext context;//= new ClassPathXmlApplicationContext("spring-context.xml")

	public SuperRestClass() {
		// TODO Auto-generated constructor stub
		//context = new ClassPathXmlApplicationContext("spring-context.xml");
	}

//	@PostConstruct
//	private void init() {
//		context = new ClassPathXmlApplicationContext("spring-context.xml");
//	}
	
	static{
		WriteConsoleUtil.write("Iniciando Spring-context");
		context = new ClassPathXmlApplicationContext("spring-context.xml");
		WriteConsoleUtil.write("***** OK ****** spring-context iniciado");
	}
	
	
	/**
	 * 
	 * @param e
	 * @return
	 */
	protected Response getResponseException(Exception e) {
		StringBuilder error = new StringBuilder(e.getMessage());
		e.printStackTrace();
		if(e.getCause() != null){
			error.append(e.getCause().getMessage());
		}
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error.toString()).build();
	}


	public ApplicationContext getContext() {
		return context;
	}


	public void setContext() {
		System.out.println("trace descobrir lentidao:entrou super class1:"+new Date().getTime());
		this.context =new ClassPathXmlApplicationContext("spring-context.xml");
		System.out.println("trace descobrir lentidao:entrou super class2:"+new Date().getTime());
	}
	
	public void setContext(ApplicationContext context) {
		this.context = context;
	
	}
	public void inicializar(){
		setContext();
	}
}
