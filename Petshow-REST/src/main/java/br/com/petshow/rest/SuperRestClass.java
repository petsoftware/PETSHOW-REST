package br.com.petshow.rest;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import br.com.petshow.util.WriteConsoleUtil;

/**
 * Super Classe para reutilizar metodos nas outras classes REST
 * @author Rafael Rocha
 * @since versao 1.0 - dia 02/12/2016 as 09:42.
 */
public class SuperRestClass {


	private static ApplicationContext context;//= new ClassPathXmlApplicationContext("spring-context.xml")
//	private XmlWebApplicationContext context;
	
	public SuperRestClass() {
		// TODO Auto-generated constructor stub
		//context = new ClassPathXmlApplicationContext("spring-context.xml");
	}

//	@PostConstruct
//	private void init() {
//		context = new ClassPathXmlApplicationContext("spring-context.xml");
//	}
	
	static{
		context = new ClassPathXmlApplicationContext("spring-context.xml");
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
		/**
		 * In non-web applications we use ApplicationContext interface to provide beans configuration to our applications 
		 * from xml files.For example:
			ApplicationContext context = new FileSystemXmlApplicationContext("c:/foo.xml");
			OR
			ApplicationContext context = new ClassPathXmlApplicationContext("foo.xml");
			------------------------------------------------------------------------------
						FOR WEB APPLICATIONS
			------------------------------------------------------------------------------
			But in web applications we use WebApplicationContext interface instead of ApplicationContext interface to 
			provide bean configuration to our web application and to represent the spring container.
			WebApplicationContext extends ApplicationContext and has some extra features necessary for web applications.
		 */
		if(context == null){
			WriteConsoleUtil.write("Contexto do Spring ainda NÃO iniciado - Comecando inicializacao");
			context =new ClassPathXmlApplicationContext("spring-context.xml");
//			this.context =new XmlWebApplicationContext();
//			this.context.refresh();
			WriteConsoleUtil.write("******* OK - spring-context Iniciado ********** ");
		}
	}
	
//	public void setContext(ApplicationContext context) {
//	public void setContext(XmlWebApplicationContext context) {
//		context = context;
//	
//	}
	public void inicializar(){
		setContext();
	}
}
